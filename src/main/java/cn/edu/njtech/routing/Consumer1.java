package cn.edu.njtech.routing;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/12 6:45 下午
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();

        final Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare("logs_direct", "direct");
        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //绑定队列和交换机
        channel.queueBind(queue, "logs_direct", "error");
        channel.queueBind(queue, "logs_direct", "info");
        channel.queueBind(queue, "logs_direct", "warn");

        //消费消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });
    }
}
