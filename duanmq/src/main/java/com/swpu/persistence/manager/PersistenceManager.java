package com.swpu.persistence.manager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.swpu.config.ConstantConfig;
import com.swpu.imitate.mqobject.message.Message;
import com.swpu.model.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午3:27
 * 消息持久化
 **/

public abstract class PersistenceManager {

    // 消息持久化到本地
    public abstract Result<Boolean> write(Message message);

    // 读取持久化的消息
    public abstract Result<List<Message>> read();

    // 消息json数组转消息列表
    public List<Message> stringToMessageList(String string){
        String[] s = string.split(ConstantConfig.messageSplit);
        List<Message> result = new ArrayList<>();
        List<String> info = Arrays.asList(s);
        if (info.size() > 0){
            info = info.subList(0, info.size() - 1);
        }

        info.forEach(x -> {
            result.add(JSON.parseObject(x, new TypeReference<Message>(){}));
        });

        return result;
    }
}
