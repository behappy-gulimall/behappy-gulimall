package org.xiaowu.behappy.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xiaowu.behappy.gateway.filter.AccessLogGlobalFilter;
import org.xiaowu.behappy.gateway.filter.GlobalCacheRequestBodyFilter;
import org.xiaowu.behappy.gateway.filter.GlobalLogFilter;

/**
 * @author 94391
 * @calssName GatewayConfig
 * @Description 网关配置
 */
@Configuration
public class GatewayConfig {
    /**
     * 全局过滤器：请求日志打印
     */
    @Bean
    public GlobalLogFilter globalLogFilter(){
        // 该值越小权重却大，所以应根据具体项目配置。需要尽早的获取到参数，一般会是一个比较小的值
        return new GlobalLogFilter(-20);
    }

    @Bean
    public GlobalCacheRequestBodyFilter globalCacheRequestBodyFilter(){
        return new GlobalCacheRequestBodyFilter(-21);
    }

//    @Bean
//    public AccessLogGlobalFilter accessLogGlobalFilter(){
//        return new AccessLogGlobalFilter(-20);
//    }
}
