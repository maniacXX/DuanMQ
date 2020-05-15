package com.swpu.imitate.mqobject.consumer;

import com.swpu.imitate.mqobject.message.Message;
import com.swpu.imitate.mqobject.topic.NotRepeatConsumeTopic;
import com.swpu.model.Result;
import redis.clients.jedis.Jedis;

/**
 * @author linyin
 * @date 2020/5/15
 * @time 上午11:24
 **/

public class NotRepeatConsumer{

    public Result<String> consume(Message message) {

        Jedis jedis = new Jedis("39.108.191.21", 6379);

        // 判断前加锁，避免其他消费者重复判断后消费
        NotRepeatConsumeTopic.lock.lock();

        // 该偏移在Redis中存在，说明已经消费过是重复消费
        if (jedis.exists(String.valueOf(message.getOffset()))){
            System.out.println("offset为" + message.getOffset() + "的消息已被消费过，此处重复接收，已做丢弃保证幂等");

            // 存offset到Redis，重置过期时间为24h
            jedis.setex(String.valueOf(message.getOffset()), 24 * 60 * 60, "notRepeatConsume");

            // 解锁
            NotRepeatConsumeTopic.lock.unlock();
            return Result.success("重复消费,已幂等处理");
        }else {
            // 未被消费
            System.out.println("offset为" + message.getOffset() + "已被消费");

            // 存offset到Redis，过期时间为24h
            jedis.setex(String.valueOf(message.getOffset()), 24 * 60 * 60, "notRepeatConsume");

            // 解锁
            NotRepeatConsumeTopic.lock.unlock();
            return Result.success("正常消费");
        }
    }
}

