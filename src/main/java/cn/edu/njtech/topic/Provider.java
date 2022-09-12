package cn.edu.njtech.topic;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author tim
 * @date 2022/9/12 6:49 下午
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        final Connection connection = RabbitMQUtil.getConnection();

        final Channel channel = connection.createChannel();
        //生命交换机和交换机类型 topic 使用动态路由(通配符方式)

        channel.exchangeDeclare("topics", "topic");

        String routekey = "user.save.test";//动态路由key
        //发布消息
        channel.basicPublish("topics", routekey, null, ("这是路由中的动态订阅模型,route key: [" + routekey + "]").getBytes());

        RabbitMQUtil.closeConnectionAndChannel(channel, connection);
    }
}
