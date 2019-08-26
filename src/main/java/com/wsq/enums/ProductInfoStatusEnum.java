package com.wsq.enums;

import lombok.Getter;

/**
 * 商品状态
 * @author wsq
 *         Created by Tp on 2019/5/15.
 */
@Getter
public enum ProductInfoStatusEnum implements CodeEnum{

    UP(0,"在架"),
    DOWN(1,"下架");
    private Integer code;

    private String msg;

    ProductInfoStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
