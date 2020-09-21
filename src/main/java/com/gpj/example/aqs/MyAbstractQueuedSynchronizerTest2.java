package com.gpj.example.aqs;

import org.junit.Test;

import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/14 11:16 上午
 */
public class MyAbstractQueuedSynchronizerTest2 {

    private static String unlock = "unlock";
    private static String tryUnlock = "tryUnlock";

    public static void main(String[] args) throws InterruptedException {
        new MyAbstractQueuedSynchronizerTest2().testAbstractQueuedSynchronizer();
    }

    public void testAbstractQueuedSynchronizer() throws InterruptedException {
        Lock lock = new ReentrantLock(true);

        Runnable runnable0 = new ReentrantLockThread(lock);
        Thread thread0 = new Thread(runnable0);
        thread0.setName("线程0");

        Runnable runnable1 = new ReentrantLockThread(lock);
        Thread thread1 = new Thread(runnable1);
        thread1.setName("线程1");

        Runnable runnable2 = new ReentrantLockThread(lock);
        Thread thread2 = new Thread(runnable2);
        thread2.setName("线程2");

        thread0.start();
        //Thread.sleep(100);
        thread1.start();
        //thread2.start();

        Scanner scanner = new Scanner(System.in);
        for (;;) {
            String s = scanner.nextLine();
            System.out.println(s);
            tryUnlock = s;
        }

        //for (;;) {}
    }

    private static class ReentrantLockThread implements Runnable {

        private Lock lock;

        public ReentrantLockThread(Lock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                lock.lock();
                for (;;){
                    if (unlock.equals(tryUnlock) && "线程0".equals(Thread.currentThread().getName())) {
                        break;
                    }
                }
            } finally {
                lock.unlock();
            }
        }
    }

}
