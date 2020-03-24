package com.swpu.imitate.mqobject.consumer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:30
 * 消费者接口，模拟消费者工作
 **/
public interface Consumer {

    /**
     * 消费者消费消息
     * @param message 消息体
     * @return 消费后返回消费情况
     */
    public Result<Boolean> consume(Message message);
}

