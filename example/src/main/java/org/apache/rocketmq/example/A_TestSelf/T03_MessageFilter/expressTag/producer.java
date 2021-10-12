package org.apache.rocketmq.example.A_TestSelf.T03_MessageFilter.expressTag;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.example.A_TestSelf.T00_Utils.UtilsCollection;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;

/**
 * @author LeiLiMin
 * @Date 2021/10/12 11:03
 * @Description 消息过滤-表达式-Tag模式
 */
public class producer {
    public static void main(String[] args) throws UnsupportedEncodingException, MQClientException {
        DefaultMQProducer producer = UtilsCollection.getProducer("ExpressByTagGroupName");
        producer.start();

        // 重点是Tag的不同
        for (int i = 0; i < 10; i++) {
            if(i%2==0){
                Message msg=new Message("TopicFilter7","TOPIC_TAG_ALl","OrderID001",
                        "Hello World".getBytes(RemotingHelper.DEFAULT_CHARSET));
            }else {
                Message msg=new Message("TopicFilter7","TOPIC_TAG_ORD","OrderID001",
                        "Hello World".getBytes(RemotingHelper.DEFAULT_CHARSET));
            }
        }
    }
}
