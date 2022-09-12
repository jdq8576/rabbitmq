package cn.edu.njtech.helloworld;

import cn.edu.njtech.util.RabbitMQUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author tim
 * @date 2022/9/7 7:24 下午
 */
public class Provider {

    // 生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        /*
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
         */

        // 获取连接对象
        Connection connection = RabbitMQUtil.getConnection();

        // 获取通道
        Channel channel = connection.createChannel();

        // 绑定通道消息队列
        // 参数1 队列名称 队列不存在自动创建
        // 参数2 是否持久化 true 代表持久化
        // 参数3 是否独占队列 true 代表独占队列
        // 参数4 autoDelete true 消息消费完自动删除队列
        // 参数5 附加参数
        channel.queueDeclare("hello", true, false, false, null);

        // 发布消息
        // 参数1 交换机名称
        // 参数2 队列名称
        // 参数3 传递消息的额外设置
        // 参数4 消息的具体内容
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitmq".getBytes());

        channel.close();
        connection.close();
    }

}
