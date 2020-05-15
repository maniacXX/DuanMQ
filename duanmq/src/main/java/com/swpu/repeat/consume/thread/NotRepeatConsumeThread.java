package com.swpu.repeat.consume.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.swpu.config.ShopMessageConfig;
import com.swpu.exception.ListIsNullException;
import com.swpu.imitate.mqobject.consumer.Consumer;
import com.swpu.imitate.mqobject.consumer.NotRepeatConsumer;
import com.swpu.imitate.mqobject.message.Message;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author linyin
 * @date 2020/5/15
 * @time 下午8:11
 **/

public class NotRepeatConsumeThread extends Thread{
    // 使用这个线程的消费者信息
    private String consumerName = "default";

    NotRepeatConsumer consumer = new NotRepeatConsumer();

    // 存放消息的list
    private LinkedBlockingQueue<Message> queue;

    public NotRepeatConsumeThread() {
    }

    public NotRepeatConsumeThread(LinkedBlockingQueue<Message> queue, String threadName) {
        super(threadName);
        this.queue = queue;
    }

    @SneakyThrows
    @Override
    public void run() {
        if (queue == null){
            throw new ListIsNullException("ConsumerThread:" + currentThread().getName() + "List is Null");
        }
        // 只要来消息就让消费者消费，没来消息的话，take可以保证阻塞等待
        try {
            consumer.consume(queue.take());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public NotRepeatConsumer getConsumer() {
        return consumer;
    }

    public void setConsumer(NotRepeatConsumer consumer) {
        this.consumer = consumer;
    }

    public LinkedBlockingQueue<Message> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingQueue<Message> queue) {
        this.queue = queue;
    }
}

