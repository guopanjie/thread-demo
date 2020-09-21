package com.gpj.example.aqs;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：guopanjie001@ke.com
 * @description： 参考：https://www.cnblogs.com/dwlsxj/p/reentrantlock-principle-nonfairsync.html
 * @date ：2020/9/16 10:39 上午
 */
public class ReentrantLockDemo {
    public static void main(String[] args) throws Exception {
        AddDemo runnableDemo = new AddDemo();
        Thread thread0 = new Thread(runnableDemo::add);
        thread0.start();
        Thread thread1 = new Thread(runnableDemo::add);
        thread1.start();
        Thread thread2 = new Thread(runnableDemo::add);
        thread2.start();

        Thread.sleep(1000);
        thread1.interrupt();
        System.out.println(runnableDemo.getCount());
    }

    private static class AddDemo {
        private final AtomicInteger count = new AtomicInteger();
        private final ReentrantLock reentrantLock = new ReentrantLock();
        private final Condition condition = reentrantLock.newCondition();

        private void add() {
            try {
                reentrantLock.lock();
                count.getAndIncrement();
            } finally {
                reentrantLock.unlock();
            }
        }

        int getCount() {
            return count.get();
        }
    }
}
