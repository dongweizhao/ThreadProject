package com.company.test;

import com.sun.javafx.tk.Toolkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by dongweizhao on 16/7/17.
 */
public class CallableAndFuture3 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        final CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executorService);
        final Collection<Callable<Integer>> callables = new ArrayList<Callable<Integer>>();
        for (int i = 0; i < 10; i++) {
            final int taskInt = i;
            callables.add(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    System.out.println("callable " + taskInt + "is runing");
                    throw new Exception("eee");
                    //return taskInt;
                }
            });

        }
        for (Callable callable : callables) {
            completionService.submit(callable);
        }


        for (int i = 0; i < 10; i++) {
            System.out.println("i = [" + i + "]");
        }
        int size = callables.size();
        for (int i = 0; i < size; i++) {
            System.out.println("future = [" + completionService.take().get() + "]");
        }
    }
}
