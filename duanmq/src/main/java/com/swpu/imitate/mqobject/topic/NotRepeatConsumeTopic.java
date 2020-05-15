package com.swpu.imitate.mqobject.topic;

import com.swpu.imitate.mqobject.consumer.Consumer;
import com.swpu.imitate.mqobject.consumer.NotRepeatConsumer;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.producer.Producer;
import com.swpu.imitate.mqobject.producer.StableDeliveryProduce;

import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author linyin
 * @date 2020/5/15
 * @time 下午3:34
 **/

public class SingleTopic {
    // 稳定投递的生产者
    private Producer producer;

    // 避免重复消费的消费者
    private Consumer consumer;

    // 存放消息的list
    private LinkedBlockingDeque<Message> queueList;

    // 消费者线程组
    List<Thread> consumers;

    // 生产者线程组
    List<Thread> producers;

    public SingleTopic(){
        producer = new StableDeliveryProduce();
        consumer = new NotRepeatConsumer();
    }

    public void run(){

    }
}

