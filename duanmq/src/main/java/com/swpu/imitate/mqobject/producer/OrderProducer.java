package com.swpu.imitate.mqobject.producer;

import com.alibaba.fastjson.JSON;
import com.swpu.config.ShopMessageConfig;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.topic.Topic;
import com.swpu.model.Result;
import lombok.Data;

import java.util.*;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午4:11
 **/
@Data
public class OrderProducer{
    // 生产者名称
    private String producerName = "default";

    // hash一个列表存放生产者生产的消息
    private final int hashValue = Math.abs(new Random().nextInt());

    public OrderProducer() {
    }

    public OrderProducer(String producerName) {
        this.producerName = producerName;
    }

    public Result<List<Message>> produce(String customerName){
        List<Message> list = new ArrayList<>();
        Message message = new Message();
        message.setProducerName(this.producerName);
        message.setProduceTime(new Date());

        // 在拓展字段里面存一个消息的hash值，当同一个客户的消息被消费者消费时，散列到其多个消费线程的其中一个
        Map<String, String> extension = new HashMap<>();
        extension.put("hashValue", String.valueOf(Math.abs(new Random().nextInt())));
        message.setExtension(extension);

        Map<String, String> messageBody = new HashMap<>();
        messageBody.put(ShopMessageConfig.CUSTOMER, customerName);

        // 下单
        messageBody.put(ShopMessageConfig.STEP_NAME, ShopMessageConfig.PLACE_ORDER);
        messageBody.put(ShopMessageConfig.PRODUCE_RANK, "1");
        message.setMessageBody(JSON.toJSONString(messageBody));
        message.setOffset(Topic.offset.addAndGet(1));
        list.add(new Message(message));

        // 发货
        messageBody.put(ShopMessageConfig.STEP_NAME, ShopMessageConfig.DELIVER);
        messageBody.put(ShopMessageConfig.PRODUCE_RANK, "2");
        message.setMessageBody(JSON.toJSONString(messageBody));
        message.setOffset(Topic.offset.addAndGet(1));
        list.add(new Message(message));

        // 收货
        messageBody.put(ShopMessageConfig.STEP_NAME, ShopMessageConfig.RECEIVE);
        messageBody.put(ShopMessageConfig.PRODUCE_RANK, "3");
        message.setMessageBody(JSON.toJSONString(messageBody));
        message.setOffset(Topic.offset.addAndGet(1));
        list.add(new Message(message));

        // 支付
        messageBody.put(ShopMessageConfig.STEP_NAME, ShopMessageConfig.PAY);
        messageBody.put(ShopMessageConfig.PRODUCE_RANK, "4");
        message.setMessageBody(JSON.toJSONString(messageBody));
        message.setOffset(Topic.offset.addAndGet(1));
        list.add(message);

        return Result.success(list);
    }
}

