package org.xiaowu.behappy.order;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author 94391
 * @EnableAspectJAutoProxy(exposeProxy = true)：开启aspect动态代理模式, 对外暴露代理对象
 */
@EnableRabbit
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableFeignClients(value = "org.xiaowu.behappy")
@SpringBootApplication(scanBasePackages = "org.xiaowu.behappy")
public class BehappyOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BehappyOrderApplication.class, args);
    }

}
