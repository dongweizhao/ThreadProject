package com.company.Chapter06;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * 使用fork/join统计磁盘文件数,比较类请查看 CountTaskSingle
 * Created by dongweizhao on 17/6/19.
 */
public class CountTaskRecursive extends RecursiveTask{
    File file ;

    public CountTaskRecursive(File file) {
        this.file = file;
    }
    @Override
    protected Object compute() {
        Integer csum =0;
        List<CountTaskRecursive> tasklist = new ArrayList<CountTaskRecursive>() ;
        if(file.isDirectory())
        {
            for(File f:file.listFiles())
            {
                CountTaskRecursive t = new CountTaskRecursive(f);
                tasklist.add(t);
            }
        }
        else
            csum ++;
        if(!tasklist.isEmpty())
        {
            for(CountTaskRecursive t :invokeAll(tasklist))
                csum += (Integer)t.join();
        }
        return csum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("系统日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) ;
        CountTaskRecursive task = new CountTaskRecursive(new File("/Users/dongweizhao/Documents/platform"));
      //  Future<Integer> f=new ForkJoinPool().submit(task);
        int sum= (int) new ForkJoinPool().invoke(task);
        System.out.println("args = [" + "处理其他工作" + "]");
        //System.out.println("文件数:"+f.get());
        System.out.println("文件数:"+sum);
        System.out.println("系统日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) ;
        /**
         *
         *



         系统日期：2017-06-19 14:52:23.609
         文件数:843969
         系统日期：2017-06-19 14:52:44.131
         21

         系统日期：2017-06-19 14:59:56.818
         文件数:1047303
         系统日期：2017-06-19 15:00:25.386
         29


         *
         *
         *
         */
    }
}
