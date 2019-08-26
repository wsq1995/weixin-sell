package com.wsq.controller;

import com.wsq.config.ProjectURL;
import com.wsq.constant.CookieConstant;
import com.wsq.constant.RedisConstant;
import com.wsq.dataObject.SellerInfo;
import com.wsq.enums.ResultEnum;
import com.wsq.service.SellerService;
import com.wsq.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 卖家用户
 * @author wsq
 * @date 2019/7/1 12:35
 */
@Controller
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private ProjectURL projectURL;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid, HttpServletResponse response, Map<String, Object> map){

        // 1.openid去和数据库里的数据匹配
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenId(openid);
        if (sellerInfo == null) {
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            map.put("url", "/order/list");
            return new ModelAndView("common/error");
        }
        // 2.设置token到redis
        String token = UUID.randomUUID().toString();
        Integer expire = RedisConstant.EXPIRE;//过期时间
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX, token), openid, expire, TimeUnit.SECONDS);
        // 3.设置token到cookie
        CookieUtil.set(response, CookieConstant.TOKEN, token, expire);
        return new ModelAndView("redirect:"+ projectURL + "/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map){

        // 从cookie中查询
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if (cookie == null) {
            // 清除redis
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX, cookie));
        }
        // 清除cookie
        CookieUtil.set(response, CookieConstant.TOKEN, null, 0);
        map.put("msg", ResultEnum.LOGOUT_SUCCESS.getMsg());
        map.put("url", "order/list");
        return new ModelAndView("common/success",map);
    }




}
