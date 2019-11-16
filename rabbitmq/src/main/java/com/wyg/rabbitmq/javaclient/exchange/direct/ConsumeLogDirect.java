package com.wyg.rabbitmq.javaclient.exchange.direct;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * 消费产生的日志
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-16 17:06
 * @since JDK1.8
 * @version V1.0
 */

public class ConsumeLogDirect {
    private static final String HOST = "wyginfo.cn";
    private static final int PORT = 5672;
    private static final String USERNAME = "wyg";
    private static final String PASSWORD = "fHn8RLOOFXPFkIF0";
    private static final String EXCHANGE_NAME = "test04";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        String exchangeType = "direct";
        channel.exchangeDeclare(EXCHANGE_NAME, exchangeType);
        // 获取队列名
        String queueName = channel.queueDeclare().getQueue();

        // 不同获取routingKey,并对应队列与交换机绑定
        for (String arg : args) {
            String routingKey = arg;
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }

        DeliverCallback deliverCallback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("routingKey:" + delivery.getEnvelope().getRoutingKey() + ",message:" + message);

            }
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
