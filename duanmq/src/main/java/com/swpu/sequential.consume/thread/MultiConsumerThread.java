package com.swpu.sequential.consume.thread;

import com.swpu.exception.ListIsNullException;
import com.swpu.imitate.mqobject.consumer.Consumer;
import com.swpu.imitate.mqobject.consumer.MultiConsumer;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.topic.Topic;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午5:57
 **/

public class MultiConsumerThread extends Thread{

    private Consumer consumer;

    // topic给消费者分配的专属queue
    private LinkedBlockingQueue<Message> queue;

    // topic创建消费者线程时要指定消费的队列，以及消费者开启的消费线程数，还有(消费线程名称|消费者名称)
    public MultiConsumerThread(LinkedBlockingQueue<Message> queue, int threadNum, String consumerName) {
        super(consumerName);
        this.queue = queue;
        this.consumer = new MultiConsumer(consumerName, threadNum);
    }

    @Override
    public void run() {
        if (queue == null){
            throw new ListIsNullException("ConsumerThread:" + currentThread().getName() + "List is Null");
        }
        while (queue != null){
            // 只要来消息就让消费者消费，没来消息的话，take可以保证阻塞等待
            try {
                consumer.consume(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

