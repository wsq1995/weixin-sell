package com.wsq.controller;

import com.lly835.bestpay.model.PayResponse;
import com.wsq.dto.OrderMasterDto;
import com.wsq.enums.ResultEnum;
import com.wsq.exception.SellException;
import com.wsq.service.OrderMasterService;
import com.wsq.service.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author wsq
 * @date 2019/6/1 20:52
 */
@Controller
public class PayController {

    @Autowired
    private OrderMasterService masterService;

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId, @RequestParam("returnUrl") String returnUrl,Map<String, Object> map){
        //1.查询订单
        OrderMasterDto orderMasterDto = masterService.findOne(orderId);
        if (orderMasterDto == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }

        //2. 发起支付
        PayResponse payResponse = payService.create(orderMasterDto);
        map.put("orderId", payResponse);
        map.put("returnUrl", returnUrl);

        return new ModelAndView("pay/create", map);
    }

    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        //返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
