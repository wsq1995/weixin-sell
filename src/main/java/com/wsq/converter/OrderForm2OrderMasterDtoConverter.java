package com.wsq.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wsq.dataObject.OrderDetail;
import com.wsq.dto.OrderMasterDto;
import com.wsq.enums.ResultEnum;
import com.wsq.exception.SellException;
import com.wsq.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wsq
 * @date 2019/5/24 16:54
 */
@Slf4j
public class OrderForm2OrderMasterDtoConverter {
    public static OrderMasterDto converter(OrderForm orderForm){

        OrderMasterDto orderMasterDto = new OrderMasterDto();
        orderMasterDto.setBuyerName(orderForm.getName());
        orderMasterDto.setBuyerPhone(orderForm.getPhone());
        orderMasterDto.setBuyerAddress(orderForm.getAddress());
        orderMasterDto.setBuyerOpenid(orderForm.getOpenid());
        Gson gson = new Gson();
        List<OrderDetail> orderDetails = new ArrayList<>();
        try {
            orderDetails = gson.fromJson(orderForm.getItem(), new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("【对象转换】异常， string={}",orderForm.getItem());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderMasterDto.setOrderDetails(orderDetails);

        return orderMasterDto;
    }
}
