package com.swpu.repeat.consume;

import com.swpu.imitate.mqobject.topic.NotRepeatConsumeTopic;
import redis.clients.jedis.Jedis;

/**
 * @author linyin
 * @date 2020/5/15
 * @time 上午10:57
 **/

public class Main {
    public static void main(String[] args) {
        NotRepeatConsumeTopic notRepeatConsumeTopic = new NotRepeatConsumeTopic(4, 4);
    }
}

