package com.company.Chapter08;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 17/6/6.
 */
public class ExchangerTest {
    private static final Exchanger<String> ex=new Exchanger<String>();
    private static final ExecutorService executorService= Executors.newFixedThreadPool(2);

    public static void main(String[] args) throws InterruptedException {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String a="银行流水A";
                try {
                  String b= ex.exchange(a);
                    System.out.println("A录入的是:"+a +", B录入的是:"+b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        TimeUnit.SECONDS.sleep(10);
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                String b="银行流水B";
                try {
                    String a=ex.exchange(b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
executorService.shutdown();
    }

}
