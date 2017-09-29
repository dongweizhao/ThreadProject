package com.company.test;

import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 16/7/24.
 */
public class Test003 {
    static Lock lock = new ReentrantLock();
    static BlockingQueue blockingQueue = new SynchronousQueue();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            ex.execute(new MyTest());
        }
        for (int i = 0; i < 10; i++) {
            blockingQueue.put(i);
        }
        ex.shutdown();
    }

    static class MyTest implements Runnable {

        @Override
        public void run() {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + " i:" + blockingQueue.take());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}
