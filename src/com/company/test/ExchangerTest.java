package com.company.test;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程之间交互数据
 * Created by dongweizhao on 16/7/18.
 */
public class ExchangerTest {
    public static void main(String[] args) {
        final Exchanger<String> exchanger=new Exchanger<String>();
        ExecutorService ex= Executors.newCachedThreadPool();
        ex.execute(new Runnable() {
            @Override
            public void run() {
                String data="test1";
                System.out.println(Thread.currentThread().getName()+"准备交互物品："+data);
                try {
                    Thread.sleep((long) (Math.random()*1000));
                    data=exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName()+"交互的物品是："+data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ex.execute(new Runnable() {
            @Override
            public void run() {
                String data="test2";
                System.out.println(Thread.currentThread().getName()+"准备交互物品："+data);
                try {
                    Thread.sleep((long) (Math.random()*1000));
                    data=exchanger.exchange(data);
                    System.out.println(Thread.currentThread().getName()+"交互的物品是："+data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
