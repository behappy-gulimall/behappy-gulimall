package org.xiaowu.behappy.seckill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author 94391
 */
@EnableFeignClients(basePackages = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappySeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappySeckillApplication.class, args);
    }

}
