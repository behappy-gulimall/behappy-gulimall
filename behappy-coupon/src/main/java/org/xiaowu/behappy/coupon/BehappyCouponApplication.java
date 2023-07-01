package org.xiaowu.behappy.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyCouponApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyCouponApplication.class, args);
    }

}
