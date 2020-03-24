package com.swpu.persistence;

import com.swpu.persistence.thread.IOPersistenceThread;
import com.swpu.persistence.thread.NIOPersistenceThread;

/**
 * @author linyin
 * @date 2020/3/23
 * @time 下午7:46
 **/

public class Main {

    public static void main(String[] args) {
        ioPersistenceTest();
        //nioPersistenceTest();
    }

    // 用nio进行持久化
    private static void nioPersistenceTest() {
        Thread thread1 = new IOPersistenceThread();
        thread1.setName("nio_thread1");
        Thread thread2 = new IOPersistenceThread();
        thread2.setName("nio_thread2");
        Thread thread3 = new IOPersistenceThread();
        thread3.setName("nio_thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }

    // 测试传统io的持久化效率
    private static void ioPersistenceTest() {
        Thread thread1 = new IOPersistenceThread();
        thread1.setName("io_thread1");
        Thread thread2 = new IOPersistenceThread();
        thread2.setName("io_thread2");
        Thread thread3 = new IOPersistenceThread();
        thread3.setName("io_thread3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

