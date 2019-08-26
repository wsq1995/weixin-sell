package com.wsq.dto;

import lombok.Data;

/**
 * @author wsq
 * @date 2019/5/19 18:56
 */
@Data
public class CartDto {

    private String productId;

    private Integer productQuantity;

    public CartDto() {
    }

    public CartDto(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
