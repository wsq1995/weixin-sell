package com.wsq.util;

/**
 * @author wsq
 * @date 2019/6/4 20:27
 */
public class MathUtil {

    private static final Double MONEY_RANGE = 0.01;
    public static Boolean equals(Double d1, Double d2){
        double abs = Math.abs(d1 - d2);
        if (abs<MONEY_RANGE){
            return true;
        }else {
            return false;
        }
    }

}
