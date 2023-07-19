package org.xiaowu.behappy.order.listener;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.api.order.vo.OrderVo;
import org.xiaowu.behappy.order.entity.OrderEntity;
import org.xiaowu.behappy.order.service.OrderService;

import java.io.IOException;

/**
 * @author xiaowu
 * @Description: 定时关闭订单
 **/
@Slf4j
@Component
@RequiredArgsConstructor
@RabbitListener(queues = "order.release.order.queue")
public class OrderCloseListener {

    private final OrderService orderService;

    @RabbitHandler
    public void listener(OrderVo orderVo, Channel channel, Message message) throws IOException {
        log.debug("收到过期的订单信息，准备关闭订单" + orderVo.getOrderSn());
        try {
            orderService.closeOrder(orderVo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
        } catch (Exception e) {
            channel.basicReject(message.getMessageProperties().getDeliveryTag(),true);
        }
    }

}
