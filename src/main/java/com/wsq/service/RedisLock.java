package com.wsq.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author wsq
 * @date 2019/7/2 16:06
 */
@Component
@Slf4j
public class RedisLock {

    /**
     * redis 分布式锁，大多用于秒杀的例子
     */

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间 + 超时时间
     * @return
     */
    public boolean lock(String key, String value){
        if (redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }
        /**
         * 若不加此代码，可能会造成死锁
         */
        String s = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(s) && Long.parseLong(s) < System.currentTimeMillis()){
            // 获取上一个锁的时间
            String s1 = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(s1) && s1.equals(s)){
                return true;
            }
        }
        return false;
    }

    /**
     * 解锁
     * @param key
     * @param value
     * @return
     */
    public void unlock(String key, String value){
        try {
            String currentValue = redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
