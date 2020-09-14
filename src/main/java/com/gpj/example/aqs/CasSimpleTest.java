package com.gpj.example.aqs;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ：guopanjie001@ke.com
 * @description：
 * @date ：2020/9/7 9:03 下午
 */
public class CasSimpleTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafeInstance = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafeInstance.setAccessible(true);
        sun.misc.Unsafe unsafe = (Unsafe) theUnsafeInstance.get(Unsafe.class);

        CasSimpleTest casSimpleTest = new CasSimpleTest();
        boolean flag = unsafe.compareAndSwapInt(casSimpleTest, 15, 21, 21);
        System.out.println(flag);
    }
}
