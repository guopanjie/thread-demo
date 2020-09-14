package com.gpj.example.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/2 10:30 上午
 */
public class MyAbstractQueueSynchronizerTest {
    private static CyclicBarrier barrier = new CyclicBarrier(31);
    private static int a = 0;
    private static MyAbstractQueueSynchronizer test = new MyAbstractQueueSynchronizer();

    public static void main(String[] args) throws InterruptedException, BrokenBarrierException {

        /*for(int i = 0; i < 30; i++) {
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++) {
                        unlockIncrement();
                    }
                    try {
                        Thread.sleep(10000);
                        barrier.await();
                        System.out.println("已完成"+Thread.currentThread().getName() + " a= " + a);
                    } catch (Exception e) {
                        e.printStackTrace();;
                    }
                }
            });
            t.start();
        }

        System.out.println("===before unlock model a= " + a);
        barrier.await();
        System.out.println("===after unlock model a= " + a);

        System.out.println("##########################");

        barrier.reset();
        System.out.println("此时a= " + a);
        a = 0;
        for(int i = 0; i < 30; i++) {
            Thread t = new Thread(new Runnable(){
                @Override
                public void run() {
                    for(int i = 0; i < 1000; i++ ) {
                        lockIncrement();
                    }

                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }

            });
            t.start();
        }

        barrier.await();
        System.out.println("lock model a= " + a);*/

        test.lock();
        //test.unlock();
    }

    public static void unlockIncrement() {
        a++;
    }

    public static void lockIncrement() {
        test.lock();
        a++;
        test.unlock();
    }
}
