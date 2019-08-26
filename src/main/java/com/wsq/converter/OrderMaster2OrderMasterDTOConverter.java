package com.wsq.converter;

import com.wsq.dataObject.OrderMaster;
import com.wsq.dto.OrderMasterDto;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wsq
 * @date 2019/5/22 21:25
 */
public class OrderMaster2OrderMasterDTOConverter {

    public static OrderMasterDto convert(OrderMaster orderMaster ){
        OrderMasterDto orderMasterDto = new OrderMasterDto();
        BeanUtils.copyProperties(orderMaster, orderMasterDto);
        return orderMasterDto;
    }
    public static List<OrderMasterDto> convert(List<OrderMaster> orderMasters){
        return orderMasters.stream().map(e -> convert(e)).collect(Collectors.toList());

    }
}
