package com.swpu.imitate.mqobject.message;

import lombok.Data;
import java.util.Date;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:32
 * 消息类
 **/
@Data
public class Message {
    // 生产者名称
    private String producerName;

    // 消息产生时间
    private Date produceTime;

    // 消息体
    private String messageBody;
}

