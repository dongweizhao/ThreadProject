package com.company.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 阻塞队列存取获取数据示例
 * Created by dongweizhao on 16/7/19.
 */
public class BlockQueueGetData {
    public static void main(String[] args) throws InterruptedException {
        final BlockingQueue blockingQueue = new ArrayBlockingQueue(3);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; ; ) {
                    System.out.println(Thread.currentThread().getName() + "准备放入数据");
                    try {
                        Thread.sleep(1000);
                        blockingQueue.put(2);
                        System.out.println(Thread.currentThread().getName() + "放入数据完毕");
                        System.out.println(Thread.currentThread().getName() + " 中队列现有数据：" + blockingQueue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        while (true) {
            System.out.println(Thread.currentThread().getName() + "准备取数据");
            Thread.sleep(10);
            blockingQueue.take();
            System.out.println(Thread.currentThread().getName() + "获取数据完毕");
            System.out.println(Thread.currentThread().getName() + "中队列中现有数据：" + blockingQueue.size());
        }

    }
}
