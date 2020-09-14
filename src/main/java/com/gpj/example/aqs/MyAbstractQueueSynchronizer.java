package com.gpj.example.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/2 10:26 上午
 */
public class MyAbstractQueueSynchronizer {

    private final Syn syn = new Syn();

    public void lock() {
        syn.acquire(1);
    }

    public boolean tryLock() {
        return syn.tryAcquire(1);
    }

    public void unlock() {
        syn.release(1);
    }

    public boolean isLocked() {
        return syn.isHeldExclusively();
    }

    /**
     * 继承AbstractQueuedSynchronizer类
     */
    private static class Syn extends AbstractQueuedSynchronizer {
        private static final long serialVersionUID = 1L;
        //是否拥有锁
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
        //获取锁
        @Override
        public boolean tryAcquire(int acquires) {
            /*boolean b = compareAndSetState(0, 1);
            boolean b1 = compareAndSetState(0, 1);
            System.out.println("b:"+b+ " ,b1:" +b1);*/
            if(compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
        //释放锁
        @Override
        protected boolean tryRelease(int releases) {
            if(getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }
    }
}
