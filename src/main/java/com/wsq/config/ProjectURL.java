package com.wsq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wsq
 * @date 2019/6/30 23:32
 */
@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectURL {
    /**
     *微信公众账号授权url
     */
    public String wechatMpAuthorize;

    /**
     * 微信开放账号授权url
     */
    public String wechatOpenAuthorize;

    public String sell;

}
