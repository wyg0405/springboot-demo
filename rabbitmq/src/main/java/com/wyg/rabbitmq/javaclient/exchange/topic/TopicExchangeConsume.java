package com.wyg.rabbitmq.javaclient.exchange.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-16 18:13
 * @since JDK1.8
 * @version V1.0
 */

public class TopicExchangeConsume {
    private static final String HOST = "wyginfo.cn";
    private static final int PORT = 5672;
    private static final String USERNAME = "wyg";
    private static final String PASSWORD = "fHn8RLOOFXPFkIF0";
    private static final String EXCHANGE_NAME = "test05";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String exchangeType = "topic";
        channel.exchangeDeclare(EXCHANGE_NAME, exchangeType);
        // 获取队列名
        String queueName = channel.queueDeclare().getQueue();

        /*
          不同获取routingKey,并对应队列与交换机绑定 参数： 
          1.error.# 获取所有error级别
          2.#.app.# 获取所有app的日志
          3.warn.sys.* 获取以warn.sys.开头，并以任意一个字符结尾的日志
         */
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
