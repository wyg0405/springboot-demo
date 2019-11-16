package com.wyg.rabbitmq.javaclient.exchange.direct;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * direct 类型exchange 路由 生产不同级别日志
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-16 16:50
 * @since JDK1.8
 * @version V1.0
 */

public class ProduceLogDirect {
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
        // 申明一个direct类型的exchange
        channel.exchangeDeclare(EXCHANGE_NAME, exchangeType);

        // 生产20条消息，绑定不同routingKey
        for (int i = 0; i < 20; i++) {
            String routingKey = "";
            int nextInt = new Random().nextInt(3);
            if (nextInt == 0) {
                routingKey = "error";
            } else if (nextInt == 1) {
                routingKey = "warn";
            } else {
                routingKey = "info";
            }

            String message = "log no : " + i + ",this is a " + routingKey + " log";
            System.out.println(message);
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
        }

        channel.close();
        connection.close();

    }
}
