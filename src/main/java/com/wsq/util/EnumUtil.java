package com.wsq.util;

import com.wsq.enums.CodeEnum;

/**
 * @author wsq
 * @date 2019/6/11 18:12
 */
public class EnumUtil {

    /**
     * <T extends CodeEnum>是对T的说明
     * @param code
     * @param enumClass
     * @param <T>
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for (T each: enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
