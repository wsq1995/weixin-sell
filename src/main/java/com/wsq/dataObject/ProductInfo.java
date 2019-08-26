package com.wsq.dataObject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wsq.enums.ProductInfoStatusEnum;
import com.wsq.util.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * @author wsq
 *         Created by Tp on 2019/5/15.
 */
@Entity
@Table(name = "product_info")
@Data
@DynamicUpdate // 此注解为字段自动更新
public class ProductInfo {

    @Id
    private String productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private BigDecimal productPrice;

    /**
     * 库存
     */
    @Column(name = "product_stock")
    private Integer productStock;

    @Column(name = "product_description")
    private String productDec;

    @Column(name = "product_icon")
    private String productIcon;

    @Column(name = "product_status")
    private Integer productStatus;

    /**
     * 类目编号
     */
    @Column(name = "category_type")
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock,
                       String productDec, String productIcon, Integer productStatus, Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDec = productDec;
        this.productIcon = productIcon;
        this.productStatus = productStatus;
        this.categoryType = categoryType;
    }

    @JsonIgnore
    public ProductInfoStatusEnum getProductInfoStatusEnum(){
        return EnumUtil.getByCode(productStatus, ProductInfoStatusEnum.class);
    }
}
