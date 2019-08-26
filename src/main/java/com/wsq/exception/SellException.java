package com.wsq.exception;

import com.wsq.enums.ResultEnum;
import lombok.Data;

/**
 * @author wsq
 *         Created by Tp on 2019/5/19.
 */
@Data
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }
    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
