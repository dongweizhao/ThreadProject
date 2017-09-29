package com.jvm.Chapter02;

import java.nio.ByteBuffer;
import java.util.Date;

/**
 * Created by dongweizhao on 17/7/7.
 */
public class ByteBufferTest {
    public static void createByteBuffer(){
        int time = 10000000;
        Date begin = new Date();
        for(int i=0;i<time;i++){
            ByteBuffer buffer = ByteBuffer.allocate(2);
        }
        Date end = new Date();
        System.out.println(end.getTime()-begin.getTime());//67
        begin = new Date();
        for(int i=0;i<time;i++){
            ByteBuffer buffer = ByteBuffer.allocateDirect(2);
        }
        end = new Date();
        System.out.println(end.getTime()-begin.getTime());//8231
    }

    public static void insertData(){
        int time = 100000;
        Date begin = new Date();
        ByteBuffer buffer = ByteBuffer.allocate(2*time);
        for(int i=0;i<time;i++){
            buffer.putChar('a');
        }
        buffer.flip();
        for(int i=0;i<time;i++){
            buffer.getChar();
        }
        Date end = new Date();
        System.out.println(end.getTime()-begin.getTime());//2
        begin = new Date();
        ByteBuffer buffer2 = ByteBuffer.allocateDirect(2*time);
        for(int i=0;i<time;i++){
            buffer2.putChar('a');
        }
        buffer2.flip();
        for(int i=0;i<time;i++){
            buffer2.getChar();
        }
        end = new Date();
        System.out.println(end.getTime()-begin.getTime());//1
    }
    public static void main(String[] args) {
        insertData();
    }
}
