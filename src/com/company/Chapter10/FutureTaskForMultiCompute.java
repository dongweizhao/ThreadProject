package com.company.Chapter10;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dongweizhao on 17/6/18.
 */
public class FutureTaskForMultiCompute {

    public static void main(String[] args) {
        FutureTaskForMultiCompute futureTaskForMultiCompute=new FutureTaskForMultiCompute();
        List<FutureTask<Integer>> taskList = new ArrayList<FutureTask<Integer>>();
        ExecutorService exec = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            // 传入Callable对象创建FutureTask对象
            FutureTask<Integer> ft = new FutureTask<Integer>(futureTaskForMultiCompute.new ComputeTask(i, ""+i));
            taskList.add(ft);
            // 提交给线程池执行任务，也可以通过exec.invokeAll(taskList)一次性提交所有任务;
            exec.submit(ft);
        }
        System.out.println("处理其它任务");

        // 开始统计各计算线程计算结果
        Integer totalResult = 0;
        for (FutureTask<Integer> ft : taskList) {
            try {
                //FutureTask的get方法会自动阻塞,直到获取计算结果为止
                totalResult = totalResult + ft.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        // 关闭线程池
        exec.shutdown();
        System.out.println("多任务计算后的总结果是:" + totalResult);
    }

    private class ComputeTask implements Callable<Integer> {
        private Integer result = 0;
    private String taskName = "";

    public ComputeTask(Integer iniResult, String taskName){
        result = iniResult;
        this.taskName = taskName;
        System.out.println("生成子线程计算任务: "+taskName+"result:"+result);
    }

    public String getTaskName(){
        return this.taskName;
    }

    @Override
    public Integer call() throws Exception {
        // TODO Auto-generated method stub

        for (int i = 0; i < 100; i++) {
            result =+ i;
        }
        // 休眠5秒钟，观察主线程行为，预期的结果是主线程会继续执行，到要取得FutureTask的结果是等待直至完成。
        Thread.sleep(5000);
        System.out.println("子线程计算任务: "+taskName+" 执行完成,结果是:"+result);
        return result;
    }
}
}
