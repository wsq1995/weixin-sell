package com.wsq.controller;

import com.wsq.converter.OrderForm2OrderMasterDtoConverter;
import com.wsq.dto.OrderMasterDto;
import com.wsq.enums.ResultEnum;
import com.wsq.exception.SellException;
import com.wsq.form.OrderForm;
import com.wsq.service.OrderMasterService;
import com.wsq.util.ResultVOUtil;
import com.wsq.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wsq
 * @date 2019/5/24 16:26
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderMasterService masterService;

    // 创建订单
    @PostMapping("/createOrder")
    public ResultVO<Map<String, String>> createOrder(@Valid OrderForm orderForm,
                                                     BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderMasterDto orderMasterDto = OrderForm2OrderMasterDtoConverter.converter(orderForm);
        if (CollectionUtils.isEmpty(orderMasterDto.getOrderDetails())){
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderMasterDto orderMasterDto1 = masterService.create(orderMasterDto);
        Map<String, String> map = new HashMap<>();
        map.put("orderId" , orderMasterDto1.getOrderId());
        return ResultVOUtil.success(map);

    }

    //订单列表
    @RequestMapping("/orderList")
    public ResultVO<List<OrderMasterDto>> orderList(@RequestParam("openid") String openid,
                                                    @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("查询订单列表， openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page,size);
        Page<OrderMasterDto> masterDtoPage = masterService.findList(openid, pageRequest);
        return ResultVOUtil.success(masterDtoPage.getContent());
    }

    //订单详情
    @RequestMapping("/detail")
    public ResultVO<OrderMasterDto> detail(@RequestParam("openid") String openid,@RequestParam("orderid") String orderid){
        // TODO 不安全的做法
        OrderMasterDto one = masterService.findOne(orderid);
        return ResultVOUtil.success(one);
    }

    //取消订单
    @RequestMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,@RequestParam("orderid") String orderid){
        // TODO 不安全的做法
        OrderMasterDto one = masterService.findOne(orderid);
        masterService.cancel(one);
        return ResultVOUtil.success();
    }
}
