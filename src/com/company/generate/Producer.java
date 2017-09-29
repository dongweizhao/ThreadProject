package com.company.generate;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 生产者对应的多个队列
 *
 * Created by dongweizhao on 17/6/23.
 */
public class Producer implements Runnable {
    private  List<ConcurrentLinkedQueue<Integer>> queueList;
    private int start,end;
    public Producer(List<ConcurrentLinkedQueue<Integer>> queueList,int start,int end) {
        this.queueList=queueList;
        this.start=start;
        this.end=end;
    }

    @Override
    public void run() {
        for (int i=start;i<=end;i++){
            try {
                queueList.get(i%6).offer(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
      System.out.println("args = [" + Math.ceil((double) 5000_0000/6) + "]");
        DecimalFormat df = new DecimalFormat("0.00");//格式化小数
        String num = df.format((float)5000_0000/6);//返回的是String类型
        //System.out.println("args = [" + Math.ceil(Double.parseDouble(num)) + "]");
    }
}
