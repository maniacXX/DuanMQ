package com.swpu.imitate.mqobject.topic;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.sequential.consume.thread.MultiConsumerThread;
import com.swpu.sequential.consume.thread.OrderProduceThread;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午5:23
 **/
@Data
public class Topic {
    // 消息的偏移量，多个线程生产消息，所以要用原子类
    public static AtomicLong offset = new AtomicLong(0);

    // 用阻塞队列存数据，队列数量与消费者数量一致,key值用消费者id
    HashMap<String, LinkedBlockingQueue<Message>> queueMap;

    // 阻塞队列列表，用于给生产者Hash
    List<LinkedBlockingQueue<Message>> queueList;

    // 消费者线程组
    List<Thread> consumers;

    // 生产者线程组
    List<Thread> producers;

    // 初始化时要指定开启的消费者和生产者数量
    public Topic(int produceNum, int consumerNum, int consumerChildThreadNum){
        // 先初始化属性
        queueMap = new HashMap<>();
        queueList = new ArrayList<>();
        consumers = new ArrayList<>();
        producers = new ArrayList<>();

        // 初始化消费者线程组, 每个消费者线程对应消费一个list
        for (int i = 0 ; i < consumerNum; i++){
            String threadName = "MultiConsumerThread" + (i + 1);
            LinkedBlockingQueue<Message> linkedBlockingQueue = new LinkedBlockingQueue<>();
            Thread thread = new MultiConsumerThread(linkedBlockingQueue,  consumerChildThreadNum, threadName);
            consumers.add(thread);
            queueMap.put(thread.getName(), linkedBlockingQueue);
            queueList.add(linkedBlockingQueue);
        }

        // 初始化生产者线程组，生产者的初始化要在后面，保证存放消息的队列已经初始化完成
        for (int i = 0 ; i < produceNum; i++){
            Thread thread = new OrderProduceThread(queueList);
            producers.add(thread);
        }
        
        // 启动所有生产者和消费者线程
        producers.forEach(Thread::start);
        consumers.forEach(Thread::start);
    }
    
    public void stopProducers(){
        producers.forEach(thread -> {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void stopConsumers(){
        consumers.forEach(thread -> {
            try {
                thread.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    public void stopAll(){
        stopConsumers();
        stopProducers();
    }

    public void continueProducers(){
        producers.forEach(thread -> {
                thread.notify();
        });
    }

    public void continueConsumers(){
        consumers.forEach(thread -> {
            thread.notify();
        });
    }

    public void continueAll(){
        continueConsumers();
        continueProducers();
    }
}

