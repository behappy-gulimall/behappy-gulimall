package org.xiaowu.behappy.common.pay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xiaowu.behappy.common.pay.client.WxHttpClient;

/**
 * 用于微信支付专用的http client
 * @author xiaowu
 */
@Configuration
public class WxHttpConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public WxHttpClient wxHttpClient(){
        return new WxHttpClient();
    }
}
