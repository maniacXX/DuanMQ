package com.swpu.persistence.model;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:27
 * 消息持久化
 **/

public interface Persistence {

    // 消息持久化到本地
    public Result<Boolean> write(Message message) throws FileNotFoundException;

    // 读取持久化的消息
    public Result<List<Message>> read();
}
