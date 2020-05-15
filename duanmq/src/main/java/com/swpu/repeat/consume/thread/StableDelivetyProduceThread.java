package com.swpu.repeat.consume.thread;

import com.swpu.exception.ListIsNullException;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.producer.OrderProducer;
import com.swpu.imitate.mqobject.producer.StableDeliveryProduce;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author linyin
 * @date 2020/5/15
 * @time 下午5:11
 **/

public class StableDelivetyProduceThread extends Thread{

    private StableDeliveryProduce stableDeliveryProduce = new StableDeliveryProduce();

    private LinkedBlockingQueue<Message> queue;

    public StableDelivetyProduceThread(LinkedBlockingQueue<Message> queueList, String threadName) {
        super(threadName);
        this.queue = queueList;

    }

    @Override
    public void run() {
        if (queue == null){
            throw new ListIsNullException("ProduceThread:" + currentThread().getName() + "queue is Null");
        }
        while (queue != null) {
            // 让生产者生产一个消息,初始化存入ack为false表示没收到
            Message message = new Message();
            message.setExtension(new HashMap<>());
            message.getExtension().put("ack","false");

            // 匿名线程去创建消息
            new Thread(){
                @Override
                public void run() {
                    stableDeliveryProduce.produce(message);
                }
            }.start();

            // 未收到一直轮询等待
            while (! stableDeliveryProduce.isReceive()){
                // 收到消息，把消息扔进Topic的队列里面
                if (message.getExtension().get("ack").equals("true")){
                    try {
                        queue.put(message);
                        // 打印收到的消息的offset
                        System.out.println("收到消息的offset:" + message.getOffset());

                        // 确认收到消息
                        stableDeliveryProduce.receiveSwitch(true);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // 确认失败就等待3秒,此时消息重复发送
                if (stableDeliveryProduce.isReceive()){
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public StableDeliveryProduce getStableDeliveryProduce() {
        return stableDeliveryProduce;
    }

    public void setStableDeliveryProduce(StableDeliveryProduce stableDeliveryProduce) {
        this.stableDeliveryProduce = stableDeliveryProduce;
    }

    public LinkedBlockingQueue<Message> getQueue() {
        return queue;
    }

    public void setQueue(LinkedBlockingQueue<Message> queue) {
        this.queue = queue;
    }
}

