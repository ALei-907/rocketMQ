package org.apache.rocketmq.example.A_TestSelf.T02_ChooseQueue;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.example.A_TestSelf.T00_Utils.UtilsCollection;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @author LeiLiMin
 * @Date 2021/10/11 20:09
 * @Description 自由选择消息队列
 */
public class T02_ChooseConsumeQueue {
    public static void main(String[] args) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {
        String[] tags = new String[]{"TagA", "TageB", "TagC", "TagD", "TagE"};
        DefaultMQProducer producer = UtilsCollection.getProducer("ChooseConsumeGroupName");
        producer.start();

        // 核心MessageQueueSelector
        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            Message msg = new Message("TopicTestjjj", tags[i % tags.length], "KEY" + i, ("Hello RocketMQ " + i).getBytes());
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg // 这个arg就是orderId
                ) {
                    Integer id = (int) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);
            System.out.println(sendResult);
        }
    }
}
