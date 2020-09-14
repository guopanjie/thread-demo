package com.gpj.example.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/7 10:42 下午
 */
public class Counter {
    private volatile int count = 0;

    private static long offset;
    private static Unsafe unsafe;
    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
            offset = unsafe.objectFieldOffset(Counter.class.getDeclaredField("count"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void increment() {
        int before = count;
        // 失败了就重试直到成功为止
        while (!unsafe.compareAndSwapInt(this, offset, before, before + 1)) {
            before = count;
        }
    }

    public int getCount() {
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        ExecutorService threadPool = Executors.newFixedThreadPool(100);

        // 起100个线程，每个线程自增10000次
        /*IntStream.range(0, 100).forEach(i->
                threadPool.submit(()->IntStream.range(0, 10000).forEach(j->counter.increment()))
        );*/

        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> {
                for (int j =0; j < 10000; j++) {
                    counter.increment();
                }
            });
        }

        threadPool.shutdown();

        Thread.sleep(2000);

        // 打印1000000
        System.out.println(counter.getCount());
    }
}
