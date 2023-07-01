package org.xiaowu.behappy.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappySearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappySearchApplication.class, args);
    }

}
