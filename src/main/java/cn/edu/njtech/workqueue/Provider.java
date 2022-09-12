package cn.edu.njtech.workqueue;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/7 8:35 下午
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtil.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        channel.queueDeclare("work", true, false, false, null);

        for (int i = 0; i < 30; i++) {
            // 生产消息
            channel.basicPublish("", "work", null, (i + "--------> hello work queue").getBytes());
        }
        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
