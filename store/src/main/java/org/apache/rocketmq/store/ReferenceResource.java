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
package org.apache.rocketmq.store;

import java.util.concurrent.atomic.AtomicLong;

public abstract class ReferenceResource {
    protected final AtomicLong refCount = new AtomicLong(1);
    protected volatile boolean available = true;
    protected volatile boolean cleanupOver = false;
    private volatile long firstShutdownTimestamp = 0;

    public synchronized boolean hold() {
        if (this.isAvailable()) {
            if (this.refCount.getAndIncrement() > 0) {
                return true;
            } else {
                this.refCount.getAndDecrement();
            }
        }

        return false;
    }

    public boolean isAvailable() {
        return this.available;
    }

    /**
     * 关闭channel
     * 释放资源:{@link ReferenceResource#release()}
     * @param intervalForcibly
     */
    public void shutdown(final long intervalForcibly) {
        // 1.存活状态下
        if (this.available) {
            // 1.关闭活跃标志
            this.available = false;
            // 2.设置关闭时间
            this.firstShutdownTimestamp = System.currentTimeMillis();
            // 3.尝试是释放资源(release只有在引用次数小于1的时候才会释放资源)
            this.release();
        } else if (this.getRefCount() > 0) { // 关闭情况下
            // 每执行一次，减少1000的引用
            if ((System.currentTimeMillis() - this.firstShutdownTimestamp) >= intervalForcibly) {
                this.refCount.set(-1000 - this.getRefCount());
                this.release();
            }
        }
    }

    /**
     * 功能描述：将引用次数-1，如果引用数小于等于0，则执行cleanup方法
     *      1.判断是否清除完成:{@link org.apache.rocketmq.store.MappedFile.cleanup}
     */
    public void release() {
        // 1.引用计数-1
        long value = this.refCount.decrementAndGet();
        if (value > 0)
            return;
        synchronized (this) {
            // 2.判断是否清除完成(包含了清除过程)
            this.cleanupOver = this.cleanup(value);
        }
    }

    public long getRefCount() {
        return this.refCount.get();
    }

    public abstract boolean cleanup(final long currentRef);

    /**
     * 功能描述：判断释放清理完成
     * @return
     */
    public boolean isCleanupOver() {
        // 1.引用小于0 && cleanupOver=true (cleanupOver=true的触发条件为：release成功将MappedFileByteBuffer资源释放)
        return this.refCount.get() <= 0 && this.cleanupOver;
    }
}
