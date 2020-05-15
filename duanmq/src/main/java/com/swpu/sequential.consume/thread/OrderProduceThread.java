package com.swpu.sequential.consume.thread;

import com.swpu.exception.ListIsNullException;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.producer.OrderProducer;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午5:11
 **/

public class OrderProduceThread extends Thread{
    private OrderProducer orderProducer = new OrderProducer(this.getName());

    private LinkedBlockingQueue<Message> queue;

    public OrderProduceThread(List<LinkedBlockingQueue<Message>> queueList) {
        this.queue = queueList.get(orderProducer.getHashValue() % queueList.size());
    }

    @Override
    public void run() {
        if (queue == null){
            throw new ListIsNullException("ProduceThread:" + currentThread().getName() + "queue is Null");
        }
        if (queue == null){
            throw new ListIsNullException("ProduceThread:" + currentThread().getName() + "queue is Null");
        }
        while (queue != null) {
            // 让生产者生产一批消息
            List<Message> list = orderProducer.produce(String.valueOf(UUID.randomUUID())).getResult();

            // 把消息顺序扔进Topic的分组队列里面
            list.forEach(x -> {
                try {
                    queue.put(x);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            // 睡5秒再产生下一组消息
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public OrderProducer getOrderProducer() {
        return orderProducer;
    }

    public void setOrderProducer(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    public LinkedBlockingQueue<Message> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingQueue<Message> queue) {
        this.queue = queue;
    }
}

