package com.wsq.dataObject;

import com.wsq.enums.OrderStatusEnum;
import com.wsq.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */
@Entity
@Table(name = "order_master")
@Data
@DynamicUpdate
public class OrderMaster {

    @Id
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /**
     * 支付状态，默认为0 ，未支付
     */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;

    public OrderMaster() {
    }

    public OrderMaster(String orderId, String buyerName, String buyerPhone,
                       String buyerAddress, String buyerOpenid, BigDecimal orderAmount,
                       Integer orderStatus, Integer payStatus, Date createTime, Date updateTime) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
