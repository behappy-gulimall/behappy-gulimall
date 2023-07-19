package org.xiaowu.behappy.common.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 微信支付配置
 * @author xiaowu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "wxpay")
public class WxPayProperties {

    private String appId;

    private String mchId;

    private String mchKey;

    private String notifyUrl;

    private String returnUrl;

    private String cert;

}
