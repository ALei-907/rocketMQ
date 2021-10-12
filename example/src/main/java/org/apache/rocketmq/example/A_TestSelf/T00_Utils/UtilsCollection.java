package org.apache.rocketmq.example.A_TestSelf.T00_Utils;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

/**
 * @author LeiLiMin
 * @Date 2021/10/12 10:43
 * @Description 获取消息发送者
 */
public class UtilsCollection {
    public static DefaultMQProducer getProducer(String producerGroupName){
        DefaultMQProducer producer=new DefaultMQProducer(producerGroupName);
        producer.setNamesrvAddr("127.0.0.1:9876");
        return producer;
    }
    public static DefaultMQPushConsumer getConsumer(String ConsumeName){
        DefaultMQPushConsumer orderConsumer=new DefaultMQPushConsumer(ConsumeName);
        return orderConsumer;
    }
}
