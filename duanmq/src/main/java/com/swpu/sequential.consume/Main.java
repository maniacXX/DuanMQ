package com.swpu.sequential.consume;

import com.swpu.imitate.mqobject.topic.Topic;

/**
 * @author linyin
 * @date 2020/3/25
 * @time 下午4:54
 **/

public class Main {
    public static void main(String[] args) {
        Topic topic = new Topic(10, 3, 3);

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        topic.stopAll();
    }
}

