package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 16/7/3.
 */
public class SynchronizedTest implements Runnable {
    public Foo foo = new Foo();
    private Lock bankLock = new ReentrantLock();


    public static void main(String[] args) {
        SynchronizedTest synchronizedTest1 = new SynchronizedTest();
        Thread thread1 = new Thread(synchronizedTest1, "thread-1");
        Thread thread2 = new Thread(synchronizedTest1, "thread-2");
        thread1.start();
        thread2.start();
    }

    @Override
    public void run() {
        bankLock.lock();
        try {
            for (int i = 0; i < 3; i++) {
                //            synchronized (Foo.class) {
                foo.fix(30);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "：当前foo对象的x值=" + foo.getX());
                //            }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

//    public int fix(int y) {
//        return foo.fix(y);
//    }
}
