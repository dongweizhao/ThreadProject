package com.company.Chapter04;

/**
 * Created by dongweizhao on 17/9/6.
 */
public class JoinTest {
    public static void main(String[] args) {
        final Thread current=Thread.currentThread();
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    current.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" :Hello ");
            }
        });
        t.start();
        System.out.println(Thread.currentThread().getName()+" :Hello ");
    }
}
