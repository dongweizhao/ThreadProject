package com.company.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 线程信号灯，可以控制同时访问的线程个数，和锁有点类似
 * Created by dongweizhao on 16/7/18.
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        final Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            Runnable runable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                   // System.out.println(Thread.currentThread().getName() + " 进入可用并发数1：" + semaphore.drainPermits());
                    System.out.println(Thread.currentThread().getName() + " 进入,当前已有：" + (3 - semaphore.availablePermits())+" 个并发");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + " 即将离开");
                    semaphore.release();
                  // System.out.println(Thread.currentThread().getName() + " 离开可用并发数1：" + semaphore.drainPermits());
                    System.out.println(Thread.currentThread().getName() + " 离开，当前已有：" + (3 - semaphore.availablePermits())+" 并发");
                }
            };
            executorService.execute(runable);

        }
    }
}
