package com.wsq.service.impl;

import com.wsq.converter.OrderMaster2OrderMasterDTOConverter;
import com.wsq.dataObject.OrderDetail;
import com.wsq.dataObject.OrderMaster;
import com.wsq.dataObject.ProductInfo;
import com.wsq.dto.CartDto;
import com.wsq.dto.OrderMasterDto;
import com.wsq.enums.OrderStatusEnum;
import com.wsq.enums.PayStatusEnum;
import com.wsq.enums.ResultEnum;
import com.wsq.exception.SellException;
import com.wsq.repository.OrderDetailRepository;
import com.wsq.repository.OrderMasterRepository;
import com.wsq.service.OrderMasterService;
import com.wsq.service.PayService;
import com.wsq.service.ProductInfoService;
import com.wsq.service.WebSocket;
import com.wsq.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */
@Service
@Slf4j
public class OrderMasterServiceImpl implements OrderMasterService {

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderMasterRepository masterRepository;

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private PayService payService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderMasterDto create(OrderMasterDto orderMasterDto) {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);
        //1. 查询商品(数量，价格)
        for (OrderDetail orderDetail : orderMasterDto.getOrderDetails()) {
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if (productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2. 计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            //订单详情入库
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo, orderDetail);
            detailRepository.save(orderDetail);
        }

        //3.写入订单数据库(orderMaster和orderDetail)
        OrderMaster orderMaster = new OrderMaster();
        orderMasterDto.setOrderId(orderId);
        BeanUtils.copyProperties(orderMasterDto , orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);

        //4.扣库存
        List<CartDto> cartDtos = orderMasterDto.getOrderDetails().stream().map(
                e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDtos);
        webSocket.sendMessage(orderMasterDto.getOrderId());
        return orderMasterDto;
    }

    @Override
    public OrderMasterDto findOne(String orderId) {
        OrderMaster orderMaster = masterRepository.findById(orderId).get();
        if (orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        List<OrderDetail> details = detailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(details)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIT);
        }
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        BeanUtils.copyProperties(orderMaster, orderMasterDto);
        return orderMasterDto;
    }

    @Override
    public Page<OrderMasterDto> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> page = masterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderMasterDto> masterDtos = OrderMaster2OrderMasterDTOConverter.convert(page.getContent());
        return new PageImpl<OrderMasterDto>(masterDtos, pageable, page.getTotalElements());

    }

    @Override
    @Transactional
    public OrderMasterDto cancel(OrderMasterDto orderMasterDto) {
        OrderMaster orderMaster = new OrderMaster();

        // 判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}", orderMasterDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMasterDto.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【取消订单】更新失败， orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if (CollectionUtils.isEmpty(orderMasterDto.getOrderDetails())){
            log.error("【取消订单】订单中无商品详情， orderMasterDto={}", orderMasterDto);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDto> cartDtos = orderMasterDto.getOrderDetails().stream().map(
                e -> new CartDto(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.increaseStock(cartDtos);
        //如果已支付，需要退还
        if (orderMasterDto.getPayStatus().equals(PayStatusEnum.SUCCESS)){
            payService.refund(orderMasterDto);
        }
        return orderMasterDto;
    }

    // 完结订单
    @Override
    public OrderMasterDto finished(OrderMasterDto orderMasterDto) {
        // 判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确， orderId={}，orderStatus={}", orderMasterDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMasterDto.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【完结订单】更新失败， orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderMasterDto;
    }

    @Override
    @Transactional
    public OrderMasterDto paid(OrderMasterDto orderMasterDto) {
        // 判断订单状态
        if (!orderMasterDto.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付成功】订单状态不正确， orderId={}，orderStatus={}", orderMasterDto.getOrderId());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderMasterDto.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("【订单支付完成】订单支付状态不正确，orderMasterDto={}", orderMasterDto);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderMasterDto.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderMasterDto, orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if (updateResult == null){
            log.error("【订单支付完成】更新失败， orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderMasterDto;
    }

    @Override
    public Page<OrderMasterDto> findList(Pageable pageable) {
        Page<OrderMaster> masters = masterRepository.findAll(pageable);
        List<OrderMasterDto> masterDtos = OrderMaster2OrderMasterDTOConverter.convert(masters.getContent());
        return new PageImpl<OrderMasterDto>(masterDtos, pageable, masters.getTotalElements());
    }
}
