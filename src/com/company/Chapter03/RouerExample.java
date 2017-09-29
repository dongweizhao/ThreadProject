package com.company.Chapter03;

import java.util.ArrayList;
import java.util.List;

/**
 * 线程重排序测试
 * Created by dongweizhao on 16/11/12.
 */
public class RouerExample {
    public static int a = 0;
    public static boolean flag = false;


    public static void main(String[] args) throws InterruptedException {

        //RouerExample rou = new RouerExample();
        for (int n = 0; n < 100000; n++) {
            List<Thread> readList=new ArrayList<Thread>();
            Thread read = new RouterReaderThread();
            readList.add(read);
            List<Thread> writeList=new ArrayList<Thread>();
            for (int i = 0; i <16 ; i++) {
                Thread write = new RouterWriterThread();
                writeList.add(write);
            }
            for (Thread t:writeList){
                t.start();
            }
//            for (Thread t:readList){
//                t.start();
//            }
            read.start();
            for (Thread t:writeList){
                t.join();
            }
            read.join();

            a = 0;
            flag = false;
        }
    }

    static class RouterReaderThread extends Thread {

        @Override
        public void run() {
            a = 1;
            flag = true;
        }
    }

    static class RouterWriterThread extends Thread {


        @Override
        public void run() {
            if (flag) {
                int i = a * a;
                if (i == 0)
                    System.out.println("a=" + a);
            }
        }
    }
}
