package com.company.test;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by dongweizhao on 16/7/17.
 */
public class CallableAndFuture1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println("call is run");
                return new Random().nextInt();
            }
        };

        FutureTask<Integer> futureTask = new FutureTask<Integer>(callable);
        new Thread(futureTask).start();
        for (int i = 0; i <10 ; i++) {
            System.out.println("i = [" + i + "]");
        }
        System.out.println("futureTask = [" + futureTask.get() + "]");
    }
}
