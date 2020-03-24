package com.swpu.persistence;

import com.swpu.persistence.thread.PersistenceThread;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午7:46
 **/

public class Main {

    public static void main(String[] args) {
        ioPersistenceTest();

    }

    // 测试传统io的持久化效率
    private static void ioPersistenceTest() {
        Thread thread1 = new PersistenceThread();
        thread1.setName("thread1");
        Thread thread2 = new PersistenceThread();
        thread2.setName("thread2");
        Thread thread3 = new PersistenceThread();
        thread3.setName("thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

