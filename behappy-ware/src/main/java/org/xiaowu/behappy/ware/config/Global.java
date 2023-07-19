package org.xiaowu.behappy.ware.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xiaowu.behappy.common.core.utils.EnvironmentUtils;

/**
 * @author xiaowu
 */
@Configuration
public class Global {

    @Value("${spring.application.name}")
    public String applicationName;

    @Value("${spring.profiles.active}")
    public String env;

    /**
     * 初始化静态参数
     * @return 0 无实意
     */
    @Bean
    public int initStatic() {
        EnvironmentUtils.setApplicationName(applicationName);
        EnvironmentUtils.setEnv(env);
        return 0;
    }

}
