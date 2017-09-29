package com.company.Chapter06;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 基于newFixedThreadPool,如果目录下所属的文件夹大于线程数，会导致线程之间相互等待，造成死锁。
 * Created by dongweizhao on 17/6/19.
 */
public class CountTaskThead implements Callable<Integer>  {
    private File file;
    ExecutorService executorService;
    public CountTaskThead(ExecutorService executorService,File file) {
        this.executorService=executorService;
        this.file=file;
    }

    @Override
    public Integer call() throws Exception {
        Integer csum =0;
        List<Future<Integer>> tasklist = new ArrayList<Future<Integer>>() ;
        if(file.isDirectory())
        {
            for(File f:file.listFiles())
            {

                tasklist.add(executorService.submit(new CountTaskThead(executorService,f)));
            }
        }
        else
            csum ++;
        if(!tasklist.isEmpty())
        {
            for(Future<Integer> t :tasklist)
                csum += (Integer)t.get();
        }
        return csum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("系统日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) ;
        ExecutorService executorService= Executors.newFixedThreadPool(5);
        Future<Integer> future=executorService.submit(new CountTaskThead(executorService,new File("/Users/dongweizhao/Documents/platform/demo/r5")));
        System.out.println("文件数:"+future.get());
        System.out.println("系统日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) ;
        executorService.shutdown();
    }
}
