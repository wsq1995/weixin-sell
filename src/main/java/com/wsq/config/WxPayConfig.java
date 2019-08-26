package com.wsq.config;

import com.lly835.bestpay.config.WxPayH5Config;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author wsq
 * @date 2019/6/1 21:10
 */
@Data
@Component
public class WxPayConfig {

    @Autowired
    private WeChatAccountConfig weChatAccountConfig;

    @Bean
    public BestPayServiceImpl bestPayService(){
//        WxPayH5Config wxPayH5Config= new WxPayH5Config();
//        wxPayH5Config.setAppId(weChatAccountConfig.getMpAppId());
//        wxPayH5Config.setAppSecret(weChatAccountConfig.getMpAppSecret());
//        wxPayH5Config.setMchId(weChatAccountConfig.getMchId());
//        wxPayH5Config.setMchKey(weChatAccountConfig.getMchKey());
//        wxPayH5Config.setKeyPath(weChatAccountConfig.getKeyPath());
        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayH5Config(wxPayH5Config());
        return bestPayService;
    }

    @Bean
    public WxPayH5Config wxPayH5Config(){
        WxPayH5Config wxPayH5Config= new WxPayH5Config();
        wxPayH5Config.setAppId(weChatAccountConfig.getMpAppId());
        wxPayH5Config.setAppSecret(weChatAccountConfig.getMpAppSecret());
        wxPayH5Config.setMchId(weChatAccountConfig.getMchId());
        wxPayH5Config.setMchKey(weChatAccountConfig.getMchKey());
        wxPayH5Config.setKeyPath(weChatAccountConfig.getKeyPath());
        wxPayH5Config.setNotifyUrl(weChatAccountConfig.getNotifyUrl());
        return wxPayH5Config;
    }
}