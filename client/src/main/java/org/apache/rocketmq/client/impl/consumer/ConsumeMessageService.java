/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.rocketmq.client.impl.consumer;

import java.util.List;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.body.ConsumeMessageDirectlyResult;

public interface ConsumeMessageService {
    void start();

    void shutdown(long awaitTerminateMillis);

    void updateCorePoolSize(int corePoolSize);

    void incCorePoolSize();

    void decCorePoolSize();

    int getCorePoolSize();

    /**
     * 直接消费消息，主要用于通过管理命令收到消费消息
     */
    ConsumeMessageDirectlyResult consumeMessageDirectly(final MessageExt msg,   // 消息
                                                        final String brokerName // 名称
    );

    /**
     * 提交消息消费
     */
    void submitConsumeRequest(
        final List<MessageExt> msgs,        // 消息列表，默认拉取32条
        final ProcessQueue processQueue,    // 消息处理队列
        final MessageQueue messageQueue,    // 消息所属消费队列
        final boolean dispathToConsume      // 是否转发到消费线程池，并发消费时忽略该参数
    );
}
