package cn.edu.njtech.fanout;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/12 6:28 下午
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        // 获取链接对象
        Connection connection = RabbitMQUtil.getConnection();
        // 获取通道
        Channel channel = connection.createChannel();

        // 参数1 交换机名称 参数2 交换机类型
        // 如果没有自动创建
        channel.exchangeDeclare("logs", "fanout");

        // 发送消息 参数一 交换机名称 参数二 路由
        channel.basicPublish("logs", "", null, "fanout type message".getBytes());

        // 释放资源
        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
