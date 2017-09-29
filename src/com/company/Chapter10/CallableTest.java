package com.company.Chapter10;

import java.util.concurrent.*;

/**
 * Created by dongweizhao on 17/6/18.
 */
public class CallableTest {


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newSingleThreadExecutor();
//        Future<Integer> future=es.submit(new CallableDemo());
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println("主线程在执行其他任务");
//        es.shutdown();
//        if (future.get()!=null){
//            //输出获取到的结果
//            System.out.println("future.get()-->"+future.get());
//        }else{
//
//            //输出获取到的结果
//            System.out.println("future.get()未获取到结果");
//        }
//
//        System.out.println("主线程在执行完成");
        FutureTask<Integer> futureTask=new FutureTask<Integer>(new CallableDemo());
        es.submit(futureTask);
        System.out.println("主线程在执行其他任务");
        es.shutdown();
        if (futureTask.get()!=null){
            //输出获取到的结果
            System.out.println("futureTask.get()-->"+futureTask.get());
        }else{

            //输出获取到的结果
            System.out.println("futureTask.get()未获取到结果");
        }

        System.out.println("主线程在执行完成");
    }
}
class CallableDemo implements Callable<Integer>{

    private int sum;

    @Override
    public Integer call() throws Exception {
        System.out.println("Callable子线程开始计算啦！");
        Thread.sleep(2000);

        for(int i=0 ;i<5000;i++){
            sum=sum+i;
        }
        System.out.println("Callable子线程计算结束！");
        return sum;
    }
}