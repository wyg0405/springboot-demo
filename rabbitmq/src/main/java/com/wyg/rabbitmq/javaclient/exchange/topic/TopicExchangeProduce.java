package com.wyg.rabbitmq.javaclient.exchange.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * topic 交换器 生产者
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-16 18:01
 * @since JDK1.8
 * @version V1.0
 */

public class TopicExchangeProduce {
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
        // 申明一个topic类型的exchange
        channel.exchangeDeclare(EXCHANGE_NAME, exchangeType);

        channel.basicPublish(EXCHANGE_NAME, "error.sys", null, ("error.sys.001").getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warn.sys", null, ("warn.sys.002").getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "info.sys", null, ("info.sys.003").getBytes("UTF-8"));

        channel.basicPublish(EXCHANGE_NAME, "error.app", null, ("error.app.004").getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warn.app", null, ("warn.app.005").getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "info.app", null, ("info.app.006").getBytes("UTF-8"));

        channel.basicPublish(EXCHANGE_NAME, "warn.sys.0", null, ("warn.sys0.007").getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warn.sys.1", null, ("warn.sys1.008").getBytes("UTF-8"));
        channel.basicPublish(EXCHANGE_NAME, "warn.sys.2", null, ("warn.sys2.009").getBytes("UTF-8"));

        channel.close();
        connection.close();
    }

}
