package org.xiaowu.behappy.auth.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 整合Redis作为session存储
 */
@EnableRedisHttpSession
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class BehappyAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyAuthServerApplication.class, args);
    }

}
