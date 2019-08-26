package com.wsq.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * http请求返回的最外层对象
 * @author wsq
 *         Created by Tp on 2019/5/15.
 */
@Data
public class ResultVO<T> implements Serializable {


    private static final long serialVersionUID = -8227164548999141008L;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 具体内容
     */
    private T data;


}
