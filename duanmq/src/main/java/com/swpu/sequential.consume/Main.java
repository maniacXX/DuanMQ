package com.swpu.sequential.consume;

import com.swpu.imitate.mqobject.topic.SequentialConsumeTopic;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午4:54
 **/

public class Main {
    public static void main(String[] args) {
        SequentialConsumeTopic sequentialConsumeTopic = new SequentialConsumeTopic(20, 3, 3);

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sequentialConsumeTopic.stopAll();
    }
}

