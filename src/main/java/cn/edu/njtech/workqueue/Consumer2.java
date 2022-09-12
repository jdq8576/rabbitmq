package cn.edu.njtech.workqueue;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/7 8:39 下午
 */
public class Consumer2 {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();

        final Channel channel = connection.createChannel();

        channel.queueDeclare("work", true, false, false, null);

        // 设置通道一次只能消费一个消息
        channel.basicQos(1);

        // 参数1 队列名称 参数2 开始消息的自动确认机制 参数3 消费时的回调接口
        channel.basicConsume("work", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者-2：" + new String(body));
                /**
                 * channel.basicAck(deliveryTag, multiple);
                 * consumer处理成功后，通知broker删除队列中的消息，如果设置multiple=true，表示支持批量确认机制以减少网络流量
                 *
                 * 如果此时channel.basicAck(8, true);则表示前面未确认的5,6,7投递也一起确认处理完毕
                 * 如果此时channel.basicAck(8, false);则仅表示deliveryTag=8的消息已经成功处理
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        });
    }
}
