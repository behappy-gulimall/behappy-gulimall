package org.xiaowu.behappy.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyMemberApplication.class, args);
    }

}
