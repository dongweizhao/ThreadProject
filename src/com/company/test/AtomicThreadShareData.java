package com.company.test;

import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongweizhao on 16/7/17.
 */
public class AtomicThreadShareData {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(2);
        final ShareData2 shareData = new ShareData2();
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    shareData.increment();
                }
            }).start();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    shareData.decrement();
                }
            }).start();
        }

    }

}

class ShareData2 {
    AtomicInteger j = new AtomicInteger(100);

    public void increment() {
        for (int i = 0; i < 100; i++) {
            //j.getAndIncrement();
            j.getAndAdd(1);
            System.out.println(Thread.currentThread().getName() + "  increment: " + j.get() + " ");
        }
    }

    public void decrement() {
        for (int i = 0; i < 100; i++) {
            //j.getAndDecrement();
            j.getAndAdd(-1);
            System.out.println(Thread.currentThread().getName() + "  decrement: " + j.get() + " ");
        }

    }
}

