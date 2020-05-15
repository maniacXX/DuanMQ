package com.swpu.imitate.mqobject.message;

import lombok.Data;
import java.util.Date;
import java.util.Map;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:32
 * 消息类
 **/
public class Message {
    // 生产者名称
    private String producerName;

    // 偏移值
    private long offset;

    // 消息产生时间
    private Date produceTime;

    // 消息体
    private String messageBody;

    // 拓展字段
    private Map<String, String> extension;

    public Message() {
    }

    public Message(Message message) {
        this.producerName = message.getProducerName();
        this.offset = message.getOffset();
        this.produceTime = message.getProduceTime();
        this.messageBody = message.getMessageBody();
        this.extension = message.getExtension();
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public long getOffset() {
        return offset;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }

    public Date getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(Date produceTime) {
        this.produceTime = produceTime;
    }

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }

    public Map<String, String> getExtension() {
        return extension;
    }

    public void setExtension(Map<String, String> extension) {
        this.extension = extension;
    }
}

