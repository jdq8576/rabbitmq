package cn.edu.njtech.fanout;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/12 6:35 下午
 */
public class Consumer1 {
    public static void main(String[] args) throws IOException {
        // 获取链接对象
        final Connection connection = RabbitMQUtil.getConnection();

        final Channel channel = connection.createChannel();

        // 绑定交换机
        channel.exchangeDeclare("logs", "fanout");

        //创建临时队列
        String queue = channel.queueDeclare().getQueue();
        //将临时队列绑定exchange
        channel.queueBind(queue, "logs", "");
        //处理消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1: " + new String(body));
            }
        });
    }
}
