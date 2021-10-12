package org.apache.rocketmq.example.A_TestSelf.T03_MessageFilter.classFilter;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.MixAll;
import org.apache.rocketmq.example.A_TestSelf.T00_Utils.UtilsCollection;

import java.io.File;

/**
 * @author LeiLiMin
 * @Date 2021/10/12 11:59
 * @Description TODO
 */
public class Consumer {
    public static void main(String[] args) throws Exception{
        DefaultMQPushConsumer consumer = UtilsCollection.getConsumer("ConsumerGroupNamecc4");
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        // 获取消息过滤类
        File classFile = new File(classLoader.getResource("MessageFilter.java").getFile());
        // 转StringCode
        String filterCode = MixAll.file2String(classFile);
        // 注册至Broker
        consumer.subscribe("TopicTest",
                "org.apache.rocketmq.example.A_TestSelf.T03_MessageFilter.classFilter.MessageFilterImpl",
                filterCode);
    }
}
