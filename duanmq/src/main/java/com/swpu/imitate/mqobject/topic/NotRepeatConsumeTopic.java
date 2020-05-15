package com.swpu.imitate.mqobject.topic;

import com.swpu.imitate.mqobject.consumer.Consumer;
import com.swpu.imitate.mqobject.consumer.NotRepeatConsumer;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.producer.Producer;
import com.swpu.imitate.mqobject.producer.StableDeliveryProduce;
import com.swpu.repeat.consume.thread.NotRepeatConsumeThread;
import com.swpu.repeat.consume.thread.StableDelivetyProduceThread;
import com.swpu.sequential.consume.thread.OrderProduceThread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 虽然是topic，但其兼顾topic，broker，nameServer三者的作用
 * @author linyin
 * @date 2020/5/15
 * @time 下午3:34
 **/

public class NotRepeatConsumeTopic {
    public static Lock lock = new ReentrantLock();

    // 消息的偏移量，多个线程生产消息，所以要用原子类
    public static AtomicLong offset = new AtomicLong(0);

    // 避免重复消费的消费者线程组
    List<Thread> consumers;

    // 稳定投递的生产者线程组
    List<Thread> producers;

    // 存放消息的list
    private LinkedBlockingQueue<Message> queue;

    public NotRepeatConsumeTopic(int produceNum, int consumerNum){
        // 初始化属性
        producers = new ArrayList<>();
        consumers = new ArrayList<>();
        queue = new LinkedBlockingQueue<>();

        // 初始化生产者线程组，生产者的初始化要在后面，保证存放消息的队列已经初始化完成
        for (int i = 0 ; i < produceNum; i++){
            Thread thread = new StableDelivetyProduceThread(queue, "StableDelivetyProduceThread" + (i + 1));
            producers.add(thread);
        }

        // 初始化消费者
        for (int i = 0 ; i < consumerNum; i++){
            Thread thread = new NotRepeatConsumeThread(queue, "StableDelivetyProduceThread" + (i + 1));
            consumers.add(thread);
        }

        // 启动所有线程
        run();
    }

    public void run(){
        // 启动所有生产者和消费者线程
        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);
    }

    public static Lock getLock() {
        return lock;
    }

    public static void setLock(Lock lock) {
        NotRepeatConsumeTopic.lock = lock;
    }

    public static AtomicLong getOffset() {
        return offset;
    }

    public static void setOffset(AtomicLong offset) {
        NotRepeatConsumeTopic.offset = offset;
    }

    public List<Thread> getConsumers() {
        return consumers;
    }

    public void setConsumers(List<Thread> consumers) {
        this.consumers = consumers;
    }

    public List<Thread> getProducers() {
        return producers;
    }

    public void setProducers(List<Thread> producers) {
        this.producers = producers;
    }

    public LinkedBlockingQueue<Message> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingQueue<Message> queue) {
        this.queue = queue;
    }
}

