package com.company.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *线程计数器，类似观察者模式
 * Created by dongweizhao on 16/7/18.
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch cth1 = new CountDownLatch(1);
        final CountDownLatch cth2 = new CountDownLatch(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(Thread.currentThread().getName() + "准备接受命令");
                        cth1.await();
                        Thread.sleep((long) (Math.random()*1000));
                        System.out.println(Thread.currentThread().getName() + "已接受命令");
                        Thread.sleep((long) (Math.random()*1000));
                        System.out.println(Thread.currentThread().getName() + "回应结果");
                        cth2.countDown();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
         Thread.sleep((long) (Math.random()*10000));
        System.out.println(Thread.currentThread().getName()+"准备发布命令");
        cth1.countDown();
        System.out.println(Thread.currentThread().getName()+"准备已发布命令");
        cth2.await();
        System.out.println(Thread.currentThread().getName()+"已接受到所有的结果");

    }

}
