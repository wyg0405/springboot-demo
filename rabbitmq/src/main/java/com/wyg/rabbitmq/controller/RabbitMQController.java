package com.wyg.rabbitmq.controller;

import java.time.LocalDateTime;

import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author wyg0405@gmail.com
 * @date 2019-11-13 15:08
 * @since JDK1.8
 * @version V1.0
 */

@RestController
public class RabbitMQController {
    @Autowired
    RabbitMessagingTemplate rabbitMessagingTemplate;

    @GetMapping("/send")
    public String send() {
        // 交换机，为空默认为AMQP default
        String exchange = "";
        // 路由，即d队列名称
        String queueName = "test02";

        for (int i = 0; i < 10; i++) {
            String playload = LocalDateTime.now() + "消息" + i;
            rabbitMessagingTemplate.convertAndSend(queueName, playload);
        }

        return "OK";
    }

}
