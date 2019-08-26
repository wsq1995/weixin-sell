package com.wsq.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wsq
 *         Created by Tp on 2019/5/17.
 */
@Data
public class ProductInfoVO implements Serializable {


    private static final long serialVersionUID = 3839749841548931123L;
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("desc")
    private String desc;

    @JsonProperty("icon")
    private String productIcon;
}
