package com.company.Chapter05;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 中断锁
 * Created by dongweizhao on 16/11/21.
 */
public class InterruptLock {
    static Lock lock=new ReentrantLock();

    static Object syncLock=new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new LockWorker(),"Lock-Thread-1");
        Thread t2=new Thread(new LockWorker(),"Lock-Thread-2");

        Thread t3=new Thread(new SyncWorker(),"Sync-Thread-1");
        Thread t4=new Thread(new SyncWorker(),"Sync-Thread-2");

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        TimeUnit.SECONDS.sleep(2);
        t2.interrupt();//lock会被中断
        t4.interrupt();//sync不会被中断
    }

    static class LockWorker implements Runnable{

        @Override
        public void run() {
            try {
                lock.lockInterruptibly();
                while (true) {
                    TimeUnit.SECONDS.sleep(10);
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("The Thread " + Thread.currentThread().getName() + " is died");
                    }else{
                        System.out.println("The Thread " + Thread.currentThread().getName() + " going...");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    static class SyncWorker implements Runnable{

        @Override
        public void run() {
            synchronized (syncLock){
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("The Thread " + Thread.currentThread().getName() + " is died");
                    }else{
                        System.out.println("The Thread " + Thread.currentThread().getName() + " going...");
                    }
                }
            }
        }
    }
}
