package com.wsq.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.wsq.dto.OrderMasterDto;
import com.wsq.enums.ResultEnum;
import com.wsq.exception.SellException;
import com.wsq.service.OrderMasterService;
import com.wsq.service.PayService;
import com.wsq.util.JsonUtil;
import com.wsq.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wsq
 * @date 2019/6/1 20:59
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService{

    private static final String ORDER_NAME = "微信点餐";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderMasterService masterService;

    @Override
    public PayResponse create(OrderMasterDto orderMasterDto) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderMasterDto.getBuyerOpenid());
        payRequest.setOrderAmount(orderMasterDto.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderMasterDto.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[微信支付]= {}" ,payResponse);
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //1.验证签名
        //2.支付的状态
        //3.支付金额
        //4.支付人(下单人 == 支付人)
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付异步通知， payResponse={}", JsonUtil.toJson(payResponse));
        //修改订单支付状态
        //查询订单
        OrderMasterDto masterDto = masterService.findOne(payResponse.getOrderId());
        if (masterDto == null) {
            log.error("[微信支付异步通知]，订单不存在，orderId = {}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        //判断金额是否一致 masterDto.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount())) != 0
        if (MathUtil.equals(payResponse.getOrderAmount() , masterDto.getOrderAmount().doubleValue())){
            log.error("[微信支付异步通知]订单金额不一致，orderId={}，微信通知金额={},系统金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),masterDto.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);

        }
        masterService.paid(masterDto);
        return payResponse;
    }

    /**
     * 退款
     * @param masterDto
     */
    @Override
    public RefundResponse refund(OrderMasterDto masterDto) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(masterDto.getOrderId());
        refundRequest.setOrderAmount(masterDto.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款]response={}",refundResponse);
        return refundResponse;
    }
}
