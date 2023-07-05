package org.xiaowu.behappy.thirdparty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 94391
 */
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyThirdPartyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyThirdPartyApplication.class, args);
    }

}
