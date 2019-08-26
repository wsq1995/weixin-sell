package com.wsq.enums;

import lombok.Getter;

/**
 * @author wsq
 *         Created by Tp on 2019/5/18.
 */
@Getter
public enum PayStatusEnum implements CodeEnum{

    WAIT(0 ,"等待支付"),
    SUCCESS(1, "支付成功");

    private Integer code;

    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
