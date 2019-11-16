package com.wyg.rabbitmq.javaclient;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * 客户端接收消息
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-15 15:33
 * @since JDK1.8
 * @version V1.0
 */

public class Receiver {
    private static final String HOST = "wyginfo.cn";
    private static final int PORT = 5672;
    private static final String USERNAME = "wyg";
    private static final String PASSWORD = "fHn8RLOOFXPFkIF0";
    private static final String QUEUE_NAME = "test03";

    public static void main(String[] args) throws IOException, TimeoutException {
        // create a connection to the server
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {
                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println(message);

            }
        };
        // receive message
        String consume = channel.basicConsume(QUEUE_NAME, true, callback, consumerTag -> {
        });
    }
}
