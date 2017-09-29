package com.company.Chapter04;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 16/11/16.
 */
public class WaitNotify {
    static Object lock = new Object();
    static Lock lock1 = new ReentrantLock();
    static Condition condition = lock1.newCondition();
    static CountDownLatch countDownLatch = new CountDownLatch(1);
     static AtomicInteger a=new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
      //  Thread wait = new Thread(new Wait(), "Wait");

        for (int i = 0; i < 20; i++) {
            Thread w = new Thread(new Wait(), "Wait-"+i);
            w.start();
        }
//        Thread nofity1 = new Thread(new Nofity(), "nofity1");
//        Thread nofity2 = new Thread(new Nofity(), "nofity2");
//        wait.start();
//        nofity1.start();
//        nofity2.start();
        countDownLatch.countDown();



    }

    static class Wait implements Runnable {

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                //lock.lock();
                System.out.println(Thread.currentThread().getName() + " wating started");
                try {
                    while (a.get()>=10) {
                        long mind = 10;
                        long now = System.currentTimeMillis() + mind;
                        lock.wait(mind);
                        long fre = now - System.currentTimeMillis();
                        if (fre <= 0) {
                            System.out.println(Thread.currentThread().getName() + "超时,超时时间:" + fre);
                        } else {
                            System.out.println(Thread.currentThread().getName() + "唤醒");
                        }
                    }
                    a.incrementAndGet();
                    //condition.w(1, TimeUnit.SECONDS);
//                condition.await(5,TimeUnit.SECONDS);
                    System.out.println(Thread.currentThread().getName() + " wating end");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(Thread.currentThread().getName() + "wating finsh");
                    //lock.unlock();
                }
            }


        }
    }

    static class Nofity implements Runnable {

        @Override
        public void run() {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                //lock.lock();
                System.out.println(Thread.currentThread().getName() + " Nofity started");
                try {
                    TimeUnit.SECONDS.sleep(5);
                    lock.notify();
//                condition.signal();
                    System.out.println(Thread.currentThread().getName() + " nofity end");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //  lock.unlock();
                }
            }

        }
    }
}
