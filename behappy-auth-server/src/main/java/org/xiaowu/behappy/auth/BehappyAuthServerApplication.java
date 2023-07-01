package org.xiaowu.behappy.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyAuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyAuthServerApplication.class, args);
    }

}
