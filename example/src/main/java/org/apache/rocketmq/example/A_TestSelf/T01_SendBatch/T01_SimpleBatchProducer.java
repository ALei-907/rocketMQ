package org.apache.rocketmq.example.A_TestSelf.T01_SendBatch;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LeiLiMin
 * @Date 2021/10/11 20:02
 * @Description 批量发送消息
 */
public class T01_SimpleBatchProducer {
    public static void main(String[] args) throws Exception {
        // 1.创建消息生产者
        DefaultMQProducer producer=new DefaultMQProducer("BatchProducerGroupName");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        // 2.设置主题，创建消息
        String topic="BatchTest";
        List<Message> messages=new ArrayList<>();

        /**
         * 设置Message的key可以使用key来查找消息
         * 结合Index文件进行hash查找
         */
        messages.add(new Message(topic,"Tag","OrderID001","Hello world 1".getBytes()));
        messages.add(new Message(topic,"Tag","OrderID002","Hello world 2".getBytes()));
        messages.add(new Message(topic,"Tag","OrderID003","Hello world 3".getBytes()));

        // 3.消息发送
        System.out.println(producer.send(messages));

    }
}
