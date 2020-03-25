package com.swpu.imitate.mqobject.consumer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 上午10:06
 **/

public class MultiThreadConsumer implements Consumer{
    @Override
    public Result<Boolean> consume(Message message) {
        return null;
    }
}

