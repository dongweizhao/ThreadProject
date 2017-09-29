package com.company.test;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongweizhao on 16/7/17.
 */
public class MutliTheadShareData {

    public static void main(String[] args) {
        final ShareData shareData = new ShareData();
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

class ShareData {
    private int j = 100;

    public synchronized void increment() {
        for (int i = 0; i < 100; i++) {
            j++;
            System.out.println(Thread.currentThread().getName() + "  increment: " + j + " ");
        }
    }

    public synchronized void decrement() {
        for (int i = 0; i < 100; i++) {
            j--;
            System.out.println(Thread.currentThread().getName() + "  decrement: " + j + " ");
        }

    }
}

