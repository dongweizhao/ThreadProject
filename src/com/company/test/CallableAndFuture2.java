package com.company.test;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by dongweizhao on 16/7/17.
 */
public class CallableAndFuture2 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Integer> future=Executors.newSingleThreadExecutor().submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("Executors call this run");
                return new Random().nextInt();
            }
        });
        for (int i = 0; i <10 ; i++) {
            System.out.println("i = [" + i + "]");
        }
        System.out.println("future:"+future.get());
    }
}
