package com.wyg.rabbitmq.queue;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *
 * @author wyg0405@gmail.com
 * @date 2019-11-13 18:53
 * @since JDK1.8
 * @version V1.0
 */

public class RabbitMQPublisher {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("wyginfo.cn");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("wyg");
        connectionFactory.setPassword("fHn8RLOOFXPFkIF0");
        Connection connection = connectionFactory.newConnection();
        Channel channel = connection.createChannel();

        String exchange = "";
        String routingKey = "test01";
        String body = System.currentTimeMillis() + " 生产数据";
        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchange, routingKey, null, body.getBytes());
        }
        channel.close();
    }
}
