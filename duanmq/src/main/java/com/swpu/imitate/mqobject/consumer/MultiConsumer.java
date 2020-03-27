package com.swpu.imitate.mqobject.consumer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;
import com.swpu.sequential.consume.thread.SingleConsumeThread;
import lombok.Data;

import java.util.*;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 上午10:06
 **/
@Data
public class MultiConsumer implements Consumer{
    // 消费者的名字
    private String consumerName = "default";

    // 消费者开启的线程数=list数量
    private int threadNum = 1;

    // 消费者下每个消费线程对应一个list
    Map<String, LinkedList<Message>> messagesMap;

    // 消息list的集合，可以通过生产者生产的消息hash到不同的列表里
    List<LinkedList<Message>> messagesList;

    // 线程集合
    List<Thread> threadList;

    public MultiConsumer(String consumerName, int threadNum) {
        this.consumerName = consumerName;
        this.threadNum = threadNum;

        this.messagesMap = new HashMap<>();
        this.messagesList = new ArrayList<>();
        this.threadList = new ArrayList<>();

        for (int i = 0 ; i < threadNum ; i++){
            LinkedList<Message> messages = new LinkedList<>();
            Thread thread = new SingleConsumeThread((consumerName + "thread" + (i+1)), consumerName, messages);

            // 把对应关系存储起来
            messagesList.add(messages);
            messagesMap.put(thread.getName(), messages);
            threadList.add(thread);
        }

        // 把消费线程全部启动
        threadList.forEach(Thread::start);
    }

    @Override
    public Result<Boolean> consume(Message message) {
        if (messagesMap == null || messagesList == null || threadList == null){
            return Result.fail(false);
        }

        // 把收到的消息散列到对应的集合
        Map<String, String> extension = message.getExtension();
        if (extension != null && extension.get("hashValue") != null){
            int hashValue = Integer.parseInt(extension.get("hashValue"));
            LinkedList<Message> messages = messagesList.get(hashValue % messagesList.size());
            synchronized (messages){
                messages.offer(message);
                messages.notify();
            }
        }

        return Result.success(true);
    }
}

