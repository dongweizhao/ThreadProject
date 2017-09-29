package com.company.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列实现两个线程之间有序切换工作。
 * Created by dongweizhao on 16/7/19.
 */
public class BlockQueueCommuntion {


    public static void main(String[] args) throws InterruptedException {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    try {
                        business.sub(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        for (int i = 0; i < 50; i++) {
            business.main(i);
        }
    }

    static class Business {
        BlockingQueue blockingQueue1 = new ArrayBlockingQueue(1);
        BlockingQueue blockingQueue2 = new ArrayBlockingQueue(1);

        {
            try {
                blockingQueue2.put(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void sub(int i) throws InterruptedException {
            blockingQueue1.put(1);
            for (int j = 1; j < 11; j++) {
                System.out.println("sub loop j=[" + j + "] i = [" + i + "]");
            }
            blockingQueue2.take();
        }

        public void main(int i) throws InterruptedException {
            blockingQueue2.put(1);
            for (int j = 1; j < 101; j++) {
                System.out.println("main loop j=[" + j + "] i = [" + i + "]");
            }
            blockingQueue1.take();
        }
    }
}
