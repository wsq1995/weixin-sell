package com.wsq.enums;

import lombok.Getter;

/**
 * @author wsq
 *         Created by Tp on 2019/5/19.
 */
@Getter
public enum ResultEnum {

    SUCCESS(0,"成功"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "商品库存不正确"),
    ORDER_NOT_EXIT(12, "订单不存在"),
    ORDERDETAIL_NOT_EXIT(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14, "订单不正确"),
    ORDER_UPDATE_FAIL(15, "订单更新失败"),
    ORDER_DETAIL_EMPTY(16, "订单详情为空"),
    ORDER_PAY_STATUS_ERROR(17, "订单支付状态不正确"),
    PARAM_ERROR(18, "参数不正确"),
    CART_EMPTY(19, "购物车为空"),
    ORDER_OWNER_ERROR(),
    WECHAT_MP_ERROR(20,"微信公众账号方面错误"),
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),
    ORDER_CANCEL_SUCCESS(22, "订单取消成功"),
    ORDER_FINISH_SUCCESS(23, "订单完结成功"),
    PRODUCT_STATUS_ERROR(24, "商品状态不正确"),
    LOGIN_FAIL(25, "登录失败"),
    LOGOUT_SUCCESS(26, "登出成功")
    ;

    private Integer code;

    private String msg;

    ResultEnum() {
    }

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
