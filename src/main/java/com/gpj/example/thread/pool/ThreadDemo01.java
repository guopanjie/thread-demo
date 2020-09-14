package com.gpj.example.thread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author ：guopanjie001@ke.com
 * @description： Executors
 * @date ：2020/8/26 4:24 下午
 */
public class ThreadDemo01 {
    public static void main(String[] args) {
        //创建固定线程个数为十个的线程池
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        //Executors.newSingleThreadExecutor()
        //Executors.newCachedThreadPool();
        //Executors.newScheduledThreadPool(10);

        //new一个Runnable接口的对象
        Runnable number = () -> {
            for (int i = 0; i <= 100; i++) {
                if (i % 2 == 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
            }
        };
        Runnable number1 = () -> {
            for (int i = 0; i < 100; i++) {
                if (i % 2 == 1) {
                    System.out.println(Thread.currentThread().getName() + ":" + i);
                }
            }
        };

        //执行线程,最多十个
        executorService.execute(number1);
        //适合适用于Runnable
        executorService.execute(number);

        //executorService.submit();//适合使用于Callable
        //关闭线程池
        executorService.shutdown();
    }
}
