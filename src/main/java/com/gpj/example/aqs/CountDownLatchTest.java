package com.gpj.example.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/23 12:04 下午
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        int N = 10;
        CountDownLatch doneSignal = new CountDownLatch(N);
        Executor e = Executors.newFixedThreadPool(8);

        // 创建 N 个任务，提交给线程池来执行
        // create and start threads
        for (int i = 0; i < N; ++i) {
            e.execute(new WorkerRunnable(doneSignal, i));
        }

        // 等待所有的任务完成，这个方法才会返回
        // wait for all to finish
        doneSignal.await();
    }
}
class WorkerRunnable implements Runnable {
    private final CountDownLatch doneSignal;
    private final int i;

    WorkerRunnable(CountDownLatch doneSignal, int i) {
        this.doneSignal = doneSignal;
        this.i = i;
    }

    @Override
    public void run() {
        doWork(i);
        // 这个线程的任务完成了，调用 countDown 方法
        doneSignal.countDown();
    }

    void doWork(int i) {
        System.out.println(i);
    }
}