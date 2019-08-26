package com.wsq.dataObject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */
@Entity
@Table(name = "order_detail")
@Data
public class OrderDetail {

    @Id
    private String detailId;

    private String orderId;

    private String productId;

    private String productName;

    private BigDecimal productPrice;

    /**
     * 商品数量
     */
    private Integer productQuantity;

    private String productIcon;

    public OrderDetail() {
    }

    public OrderDetail(String detailId, String orderId, String productId, String productName,
                       BigDecimal productPrice, Integer productQuantity,
                       String productIcon) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
    }
}
