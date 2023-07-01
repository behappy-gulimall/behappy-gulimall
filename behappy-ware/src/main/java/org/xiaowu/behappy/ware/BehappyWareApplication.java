package org.xiaowu.behappy.ware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyWareApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyWareApplication.class, args);
    }

}
