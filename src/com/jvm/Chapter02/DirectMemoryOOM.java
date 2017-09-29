package com.jvm.Chapter02;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;

/**-Xms20m -Xmx20m -XX:MaxDirectMemorySize=10M -XX:+HeapDumpOnOutOfMemoryError
 * -XX:MaxDirectMemorySize=10M
 * Created by dongweizhao on 17/7/7.
 */
public class DirectMemoryOOM {
    private static  final  int _1MB=1024*1024;

    public static void main(String[] args) throws IllegalAccessException {
//        Field fileld=Unsafe.class.getDeclaredFields()[0];
//        fileld.setAccessible(true);
//        Unsafe unsafe= (Unsafe) fileld.get(null);
//        while (true){
//            unsafe.allocateMemory(_1MB);
//        }
        while (true){
            ByteBuffer bb = ByteBuffer.allocateDirect(1024*1024);
        }


    }
}
