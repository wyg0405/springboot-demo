package com.wyg.rabbitmq.javaclient.dispatch;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/**
 * 公平分发
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-15 20:28
 * @since JDK1.8
 * @version V1.0
 */

public class FairDispatchSender {
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

        // declare a queue for us to send to;
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        // send message
        for (int i = 0; i < 20; i++) {
            String message = "第" + i + "消息，hello rabbitmq;你好，rabbitmq";
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
            System.out.println(message);
        }

        channel.close();
        connection.close();
    }
}
