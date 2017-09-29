package com.company.generate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dongweizhao on 17/6/24.
 */
public class ConsumerShareQueue implements Callable {
    private ConcurrentLinkedQueue<DataVo> concurrentLinkedQueue;
    private ExecutorService executorService;
    private FileOutputStream[] fop=new FileOutputStream[6];

    public ConsumerShareQueue(ConcurrentLinkedQueue<DataVo> concurrentLinkedQueue, ExecutorService executorService, FileOutputStream[] fop) {
        this.concurrentLinkedQueue = concurrentLinkedQueue;
        this.executorService = executorService;
        this.fop = fop;
    }

    @Override
    public Object call() throws Exception {

        while (true){
            //判断生产者是否结束,并且队列无值,则退出。
            if (executorService.isTerminated()&&concurrentLinkedQueue.isEmpty()){
                return true;
            }

            DataVo dataVo=concurrentLinkedQueue.poll();
            if (dataVo!=null){
                StringBuffer stringBuffer = new StringBuffer(dataVo.getData());
                stringBuffer.append("\n");
                fop[dataVo.getNode()].write(stringBuffer.toString().getBytes());
                dataVo=null;
            }
        }
    }

    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        double data_c=50000000;//数据量
        int pthead_c=6;//生产者线程数
        int avg= (int) Math.ceil(data_c/pthead_c);//每个线程平均分配的数据量
        int count=6;//数据节点
        int cthead_c=20;//消费者线程

        System.out.println("开始时间 = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
        ConcurrentLinkedQueue<DataVo> concurrentLinkedQueue=new ConcurrentLinkedQueue<DataVo>();

        ExecutorService producerService= Executors.newFixedThreadPool(pthead_c);

        ExecutorService consumerService= Executors.newFixedThreadPool(cthead_c);

        //创建生产者线程池
        int start=0,end=0;
        for (int i=1;i<=pthead_c;i++){
            producerService.submit(new ProducerShareQueue(concurrentLinkedQueue,start=i==1?1:end+1,end=start+avg-1>data_c?(int) data_c:start+avg-1,count));
        }

        FileOutputStream [] fop=new FileOutputStream[6];
        for (int j=0;j<count;j++){
            fop[j]=new FileOutputStream(new File("/Users/dongweizhao/Desktop/test1/file_"+j+".txt"));
        }
        //创建消费线程
        List<Future> consumerFuture=new ArrayList<Future>();
        for (int j=0;j<cthead_c;j++){
            consumerFuture.add(consumerService.submit(new ConsumerShareQueue(concurrentLinkedQueue,producerService,fop)));
        }
        producerService.shutdown();
        consumerService.shutdown();
        for (Future future:consumerFuture){
            future.get();
        }
        for (int j=0;j<count;j++){
            fop[j].flush();
            fop[j].close();
        }
        System.out.println("结束时间 = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
    }
}
