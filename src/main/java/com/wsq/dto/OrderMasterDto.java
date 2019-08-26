package com.wsq.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.wsq.dataObject.OrderDetail;
import com.wsq.enums.OrderStatusEnum;
import com.wsq.enums.PayStatusEnum;
import com.wsq.util.EnumUtil;
import com.wsq.util.serialize.Date2LongSerialize;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author wsq
 *         Created by Tp on 2019/5/19.
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) // 此注释为不返回字段为null的字段，这个已经被下面的注释替换掉
//@JsonInclude(JsonInclude.Include.NON_NULL) 在配置文件里面有做配置
public class OrderMasterDto {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    /**
     * 订单总金额
     */
    private BigDecimal orderAmount;

    // 订单状态，默认为新下单
    private Integer orderStatus ;

    /**
     * 支付状态，默认为0 ，未支付
     */
    private Integer payStatus;

    @JsonSerialize(using = Date2LongSerialize.class)
    private Date createTime;

    @JsonSerialize(using = Date2LongSerialize.class)
    private Date updateTime;

    List<OrderDetail> orderDetails;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(orderStatus, PayStatusEnum.class);
    }
}
