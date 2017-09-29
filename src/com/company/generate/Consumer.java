package com.company.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * 消费者
 * 每个消费者最小负责一个桶
 * Created by dongweizhao on 17/6/23.
 */
public class Consumer implements Callable<Boolean> {
    private List<ConcurrentLinkedQueue<Integer>> queueList;
    private int sc,ec;
    private ExecutorService executorService;
    private FileOutputStream [] fop=new FileOutputStream[6];
    public Consumer(List<ConcurrentLinkedQueue<Integer>> queueList, int sc, int ec, ExecutorService executorService) {
        this.queueList = queueList;
        this.sc = sc;
        this.ec = ec;
        this.executorService=executorService;
    }


    @Override
    public Boolean call() {
        Queue queue=null;

        try {
            int i=sc;
            while (true){

                queue=queueList.get(i);

                //判断生产者是否结束,并且队列无值,则退出。
                if (executorService.isTerminated()){
                    int  flag=sc,j=sc;
                    for (;j<=ec;j++){
                        if (queueList.get(j).isEmpty())flag++;
                        else flag--;
                    }
                    if (flag==j){
                        return true;
                    }
                }
                if (fop[i]==null){
                    fop[i]=new FileOutputStream(new File("/Users/dongweizhao/Desktop/test1/file_"+i+".txt"));
                }
                if (!queue.isEmpty()) {
                    StringBuffer stringBuffer = new StringBuffer(queue.poll().toString());
                    stringBuffer.append("\n");
                    fop[i].write(stringBuffer.toString().getBytes());
                }

                if (++i>ec){
                    i=sc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
              for (int i=sc;i<=ec;i++){
                  try {
                      fop[i].flush();
                      fop[i].close();
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
            return true;
        }

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        double data_c=5000_0000;//数据量
        int pthead_c=6;//生产者线程数
        int avg= (int) Math.ceil(data_c/pthead_c);//每个线程平均分配的数据量
        double count=6;//数据节点
        int cthead_c=6;//消费者线程
        int avgc=(int) Math.ceil(count/cthead_c);//每个线程平均分配的节点数
        System.out.println("开始时间 = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
        List<ConcurrentLinkedQueue<Integer>> queueList=new ArrayList<ConcurrentLinkedQueue<Integer>>();
        for (int i=0;i<count;i++){
            queueList.add(new ConcurrentLinkedQueue<Integer>());
        }

        ExecutorService producerService= Executors.newFixedThreadPool(pthead_c);

        ExecutorService consumerService= Executors.newFixedThreadPool(cthead_c);

        //创建生产者线程池
        int start=0,end=0;
        for (int i=1;i<=pthead_c;i++){
           producerService.submit(new Producer(queueList,start=i==1?1:end+1,end=start+avg-1>data_c?(int) data_c:start+avg-1));
        }

        //创建消费线程
        List<Future> consumerFuture=new ArrayList<Future>();
        int sc=0,ec=0;
        for (int j=0;j<cthead_c;j++){
            consumerFuture.add(consumerService.submit(new Consumer(queueList,sc=j==0?0:ec+1,ec=sc+avgc-1>count?(int)count:sc+avgc-1,producerService)));
        }
        producerService.shutdown();
        consumerService.shutdown();
        for (Future future:consumerFuture){
            future.get();
        }
        System.out.println("结束时间 = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
    }

}
