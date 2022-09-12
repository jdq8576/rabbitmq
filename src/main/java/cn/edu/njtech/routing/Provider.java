package cn.edu.njtech.routing;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/12 6:44 下午
 */
public class Provider {
    public static void main(String[] args) throws IOException {

        final Connection connection = RabbitMQUtil.getConnection();

        final Channel channel = connection.createChannel();

        //声明交换机  参数1:交换机名称 参数2:交换机类型 基于指令的Routing key转发
        channel.exchangeDeclare("logs_direct", "direct");
        String key = "error";
        //发布消息
        channel.basicPublish("logs_direct", key, null, ("指定的route key=" + key + "的消息").getBytes());

        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
