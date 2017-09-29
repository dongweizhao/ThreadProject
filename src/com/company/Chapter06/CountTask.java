package com.company.Chapter06;


import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by dongweizhao on 17/6/4.
 */
public class CountTask extends RecursiveTask<Integer> {
    private static final int THEADHOLD = 2;//阈值
    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THEADHOLD;
        if (canCompute) {
            for (int i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            int middle = (start + end) / 2;
            CountTask left = new CountTask(start, middle);
            CountTask right = new CountTask(middle + 1, end);
            left.fork();
            right.fork();
            int leftSum = left.join();
            int rightSum = right.join();
            sum = leftSum + rightSum;
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool=new ForkJoinPool();
        CountTask countTask=new CountTask(1,4);
        Future<Integer> result=forkJoinPool.submit(countTask);
        System.out.println(result.get());
    }
}
