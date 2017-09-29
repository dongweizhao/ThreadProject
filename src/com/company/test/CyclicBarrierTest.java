package com.company.test;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程删栏，当所有的线程到达指定的一个地点，并同时执行。
 * Created by dongweizhao on 16/7/18.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+" 全部到达执行标记");
            }
        });
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            Runnable runable = new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (Math.random() * 1000));
                        int num = (cyclicBarrier.getNumberWaiting() + 1);
                        System.out.println(Thread.currentThread().getName() + "即将到达集合点1,当前已有" + num + "到达" + (num == 3 ? ",都到齐了出发了" : ",正在等候"));
                        cyclicBarrier.await();

                        Thread.sleep((long) (Math.random() * 1000));
                        num = (cyclicBarrier.getNumberWaiting() + 1);
                        System.out.println(Thread.currentThread().getName() + "即将到达集合点2,当前已有" + num + "到达" + (num == 3 ? ",都到齐了出发了" : ",正在等候"));
                        cyclicBarrier.await();

                        Thread.sleep((long) (Math.random() * 1000));
                        num = (cyclicBarrier.getNumberWaiting() + 1);
                        System.out.println(Thread.currentThread().getName() + "即将到达集合点3,当前已有" + (cyclicBarrier.getNumberWaiting() + 1) + "到达" + (num == 3 ? ",都到齐了出发了" : ",正在等候"));
                        cyclicBarrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            executorService.execute(runable);
        }
    }
}
