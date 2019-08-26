package com.wsq.aspect;

import com.wsq.constant.CookieConstant;
import com.wsq.constant.RedisConstant;
import com.wsq.exception.SellerAuthorizeException;
import com.wsq.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author wsq
 * @date 2019/7/1 16:47
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.wsq.controller.Seller*.*(..))" +
            "&& !execution(public * com.wsq.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        // 获取request
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        // 查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            log.warn("[登录校验] Cookie中查不到token");
            throw new SellerAuthorizeException();
        }

        // 去redis里面查
        String s = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX));
        if (StringUtils.isEmpty(s)) {
            log.warn("[登录校验] redis中查不到token");
            throw new SellerAuthorizeException();

        }
    }
}
