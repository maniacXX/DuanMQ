package com.swpu.imitate.mqobject.producer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:28
 * 生产者类，模拟生产者的工作
 **/

public interface Producer {
    public Result<Message> produce(Object o);
}

