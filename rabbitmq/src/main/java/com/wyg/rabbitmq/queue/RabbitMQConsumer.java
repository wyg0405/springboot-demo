package com.wyg.rabbitmq.queue;

import java.io.IOException;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;

/**
 *
 * @author wyg0405@gmail.com
 * @date 2019-11-13 18:54
 * @since JDK1.8
 * @version V1.0
 */
@Component
public class RabbitMQConsumer {

    // @RabbitListener自动创建队列，并对Exchange和Queue进行绑定。
    // KEY表示路由规则routing key
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "test02", durable = "true"),
        exchange = @Exchange(value = "springdemo.direct", ignoreDeclarationExceptions = "true"),
        key = "orderRoutingKey"))
    @RabbitHandler()
    public void consumerMessage(Message message, Channel channel) throws IOException {
        System.err.println("--------------------------------------");
        System.err.println("消费端Payload: " + message.getPayload());
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        // 手工ACK,获取deliveryTag
        channel.basicAck(deliveryTag, false);

    }
}
