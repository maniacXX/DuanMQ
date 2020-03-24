package com.swpu.imitate.mqobject.consumer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午4:04
 **/

public class MQConsumer implements Consumer{
    @Override
    public Result<Boolean> consume(Message message) {
        return Result.success(true);
    }
}

