package org.xiaowu.behappy.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaowu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wb.open")
public class WbConfigProperties {

    private String clientId;

    private String clientSecret;

    private String redirectUri = "http://auth.gulimall.com/oauth2.0/weibo/success";
}
