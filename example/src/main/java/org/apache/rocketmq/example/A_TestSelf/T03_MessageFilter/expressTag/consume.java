package org.apache.rocketmq.example.A_TestSelf.T03_MessageFilter.expressTag;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.example.A_TestSelf.T00_Utils.UtilsCollection;

/**
 * @author LeiLiMin
 * @Date 2021/10/12 11:35
 * @Description 消息过滤-表达式-Tag模式
 */
public class consume {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer orderConsumer = UtilsCollection.getConsumer("Order_Data_Syn");
        DefaultMQPushConsumer kuCunConsumer = UtilsCollection.getConsumer("Order_Data_Syn");

        // *: 订阅时使用Tag表达式进行消息过滤
        /**
         * 1.多个Tag使用 || 分割
         * 2."*" 表示订阅所有消息
         */
        orderConsumer.subscribe("TopicFilter7","TOPIC_TAG_ALl || TOPIC_TAG_ORD");
        kuCunConsumer.subscribe("TopicFilter7","TOPIC_TAG_ALl || TOPIC_TAG_CAPCITY");

    }
}
