package com.gpj.example.aqs.source;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/21 11:51 上午
 */
public class AqsSourceTest {

    /** ==========================init block start====================== */
    //private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final Unsafe unsafe = getUnsafe();
    private static final long stateOffset;
    private static final long headOffset;
    private static final long tailOffset;
    private static final long waitStatusOffset;
    private static final long nextOffset;
    static {
        try {
            stateOffset = unsafe.objectFieldOffset
                    (AqsSourceTest.class.getDeclaredField("state"));
            headOffset = unsafe.objectFieldOffset
                    (AqsSourceTest.class.getDeclaredField("head"));
            tailOffset = unsafe.objectFieldOffset
                    (AqsSourceTest.class.getDeclaredField("tail"));
            waitStatusOffset = unsafe.objectFieldOffset
                    (AqsSourceTest.Node.class.getDeclaredField("waitStatus"));
            nextOffset = unsafe.objectFieldOffset
                    (AqsSourceTest.Node.class.getDeclaredField("next"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    public static Unsafe getUnsafe() {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            return (Unsafe)field.get(null);

        } catch (Exception e) {
        }
        return null;
    }
    /** ==========================init block end====================== */

    private transient volatile Node head;
    private transient volatile Node tail;
    private volatile int state;

    private Node enq(final Node node) {
        for (;;) {
            Node t = tail;
            if (t == null) {
                if (compareAndSetHead(new Node())) {
                    tail = head;
                }
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }
    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }
    private final boolean compareAndSetHead(Node update) {
        return unsafe.compareAndSwapObject(this, headOffset, null, update);
    }
    protected final boolean compareAndSetState(int expect, int update) {
        return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
    }
    public final boolean hasQueuedPredecessors() {
        Node t = tail;
        Node h = head;
        Node s;
        return h != t && ((s = h.next) == null || s.thread != Thread.currentThread());
    }

    protected final int getState() {
        return state;
    }
    protected final void setState(int newState) {
        state = newState;
    }

    public final void acquireSharedInterruptibly(int arg)
            throws InterruptedException {
        if (Thread.interrupted()) {
            throw new InterruptedException();
        }
        doAcquireSharedInterruptibly(arg);
        /*if (tryAcquireShared(arg) < 0) {
            doAcquireSharedInterruptibly(arg);
        }*/
    }
    protected int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }
    private void doAcquireSharedInterruptibly(int arg) {
        final Node node = addWaiter(Node.SHARED);
    }
    private Node addWaiter(Node mode) {
        Node node = new Node(Thread.currentThread(), mode);
        // Try the fast path of enq; backup to full enq on failure
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }
    /** ==========================node start====================== */
    static final class Node {
        static final Node SHARED = new Node();
        static final Node EXCLUSIVE = null;

        static final int CANCELLED =  1;
        static final int SIGNAL    = -1;
        static final int CONDITION = -2;
        static final int PROPAGATE = -3;

        volatile int waitStatus;
        volatile Node prev;
        volatile Node next;
        volatile Thread thread;
        Node nextWaiter;
        final boolean isShared() {
            return nextWaiter == SHARED;
        }
        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null) {
                throw new NullPointerException();
            } else {
                return p;
            }
        }
        Node() {}
        Node(Thread thread, Node mode) {
            this.nextWaiter = mode;
            this.thread = thread;
        }
        Node(Thread thread, int waitStatus) {
            this.waitStatus = waitStatus;
            this.thread = thread;
        }
    }
    /** ==========================node end====================== */
}
