package org.apache.rocketmq.example.A_TestSelf.T03_MessageFilter.classFilter;

import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author LeiLiMin
 * @Date 2021/10/12 11:55
 * @Description 消息过滤类
 */
public class MessageFilterImpl implements MessageFilter {
    @Override
    public boolean match(MessageExt msg, FilterContext context) {
        // 通过队列ID进行过滤
        String property = msg.getProperty("SequenceId");
        if (property != null) {
            int id = Integer.parseInt(property);
            if (((id % 10 == 0) && (id > 100))) {
                return true;
            }
        }
        return false;
    }
}
