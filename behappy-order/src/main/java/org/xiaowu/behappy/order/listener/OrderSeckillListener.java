package org.xiaowu.behappy.order.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.api.seckill.to.SeckillOrderTo;
import org.xiaowu.behappy.order.service.OrderService;

import java.io.IOException;

/**
 * @author xiaowu
 * @Description: 秒杀单
 **/

@Slf4j
@Component
@RabbitListener(queues = "order.seckill.order.queue")
@RequiredArgsConstructor
public class OrderSeckillListener {

    private final OrderService orderService;

    @RabbitHandler
    public void listener(SeckillOrderTo orderTo, Channel channel, Message message) throws IOException {

        log.info("准备创建秒杀单的详细信息...");

        try {
            orderService.createSeckillOrder(orderTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }

    }

}
