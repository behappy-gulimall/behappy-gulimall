package org.xiaowu.behappy.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyThirdPartyApplication.class, args);
    }

}
