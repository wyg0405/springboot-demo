package com.wyg.rabbitmq.javaclient.dispatch;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.*;

/**
 * 公平分发
 * 
 * @author wyg0405@gmail.com
 * @date 2019-11-15 20:02
 * @since JDK1.8
 * @version V1.0
 */

public class FairDispatchReciver {
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

        int prefetchCount = 1;
        // 限制发给同一个消费者不得超过1条消息
        channel.basicQos(prefetchCount);
        DeliverCallback callback = new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery delivery) throws IOException {

                try {
                    // 模拟处理业务
                    String message = new String(delivery.getBody(), "UTF-8");
                    int timeCost = new Random().nextInt(5);
                    TimeUnit.SECONDS.sleep(timeCost);
                    System.out.println(LocalDateTime.now() + ",耗时" + timeCost + "s," + message);
                } catch (InterruptedException e) {
                    // multiple - true to acknowledge all messages up to and including the supplied delivery tag; false
                    // to acknowledge just the supplied delivery tag.
                    boolean multiple = false;
                    // 手动告知
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), multiple);
                    e.printStackTrace();
                }

            }
        };
        // receive message
        // 关闭自动告知
        boolean autoAck = false;
        String consume = channel.basicConsume(QUEUE_NAME, autoAck, callback, consumerTag -> {
        });
    }
}
