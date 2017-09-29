package com.company.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 17/3/22.
 */
public class ScheduledThreadPoolExecutorTest {

    public static void main(String[] args) {
        ScheduledExecutorService scheduledExecutorService= Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+"-执行开始时间:"+System.currentTimeMillis());
                System.out.println("测试数据");
                System.out.println(Thread.currentThread().getName()+"-执行完成时间:"+System.currentTimeMillis());
            }
        },0,2, TimeUnit.SECONDS);

    }
}
