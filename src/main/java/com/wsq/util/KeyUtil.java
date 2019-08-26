package com.wsq.util;

import java.util.Random;

/**
 * @author wsq
 *         Created by Tp on 2019/5/19.
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间 + 随机数
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer i = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + String.valueOf(i);
    }
}
