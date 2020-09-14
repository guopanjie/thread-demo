package com.gpj.example.cas;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/7 8:59 下午
 */
public class CasTest {
    private volatile int s1 = 0;
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        CasTest casTest = new CasTest();

        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);

        System.out.println(casTest.s1);
        long offset = unsafe.objectFieldOffset(CasTest.class.getDeclaredField("s1"));
        boolean flag = unsafe.compareAndSwapInt(casTest, offset, 0, 3);
        System.out.println(flag);
        System.out.println(casTest.s1);
    }
}
