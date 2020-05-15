package com.swpu.sequential.consume.thread;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.swpu.config.ShopMessageConfig;
import com.swpu.exception.ListIsNullException;
import com.swpu.imitate.mqobject.message.Message;
import lombok.SneakyThrows;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午8:15
 **/

public class SingleConsumeThread extends Thread{
    // 使用这个线程的消费者信息
    private String consumerName = "default";

    private LinkedList<Message> messages;

    public SingleConsumeThread() {
    }

    public SingleConsumeThread(String threadName, String consumerName, List<Message> messages) {
        super(threadName);
        this.consumerName = consumerName;
        this.messages = (LinkedList<Message>) messages;
    }

    @SneakyThrows
    @Override
    public void run() {
        if (messages == null){
            throw new ListIsNullException("ConsumerThread:" + currentThread().getName() + "List is Null");
        }
        while (messages != null){
            synchronized (messages){
                if (messages.isEmpty()){
                    // 若没有消息，先阻塞等待生产者生产消息
                    messages.wait();
                }
                if (!messages.isEmpty()){
                    // 消费消息
                    Message message = messages.poll();
                    Map<String, String> messageBody = JSON.parseObject(message.getMessageBody(), new TypeReference<Map<String, String>>(){});
                    System.out.println("消费者信息：consumerName:" + consumerName + ", 线程：" + Thread.currentThread().getName() + "\n" +
                    "客户姓名：" + messageBody.get(ShopMessageConfig.CUSTOMER) + "产生顺位: " + messageBody.get(ShopMessageConfig.PRODUCE_RANK) + "步骤名称: " + messageBody.get(ShopMessageConfig.STEP_NAME) + "\n" +
                    "全部数据: " + message.toString() + "\n");
                }
            }
        }
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public LinkedList<Message> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<Message> messages) {
        this.messages = messages;
    }
}

