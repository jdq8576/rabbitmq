package cn.edu.njtech.topic;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/12 6:50 下午
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {

        final Connection connection = RabbitMQUtil.getConnection();

        final Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare("topics", "topic");
        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //绑定队列与交换机并设置获取交换机中动态路由
        channel.queueBind(queue, "topics", "user.*");

        //消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });
    }
}
