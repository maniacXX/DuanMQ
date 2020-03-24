package com.swpu.imitate.mqobject.producer;

import com.alibaba.fastjson.JSON;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;
import lombok.Data;

import java.util.Date;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午4:16
 **/
@Data
public class MQProducer implements Producer{
    // 生产者名称
    private String producerName = "default";

    @Override
    public Result<Message> produce(Object o) {
        Message message = new Message();
        message.setProducerName(this.producerName);
        message.setProduceTime(new Date());
        message.setMessageBody(JSON.toJSONString(o));

        return Result.success(message);
    }
}

