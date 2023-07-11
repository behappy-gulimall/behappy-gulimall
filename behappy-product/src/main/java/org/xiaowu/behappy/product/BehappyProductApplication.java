package org.xiaowu.behappy.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xiaowu
 */
@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyProductApplication.class, args);
    }

}
