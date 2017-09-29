package com.company.generate;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 生产者,多个生产者对应的一个队列
 * Created by dongweizhao on 17/6/24.
 */
public class ProducerShareQueue implements Callable {
    private ConcurrentLinkedQueue concurrentLinkedQueue;
    private int start,end,count;

    public ProducerShareQueue(ConcurrentLinkedQueue concurrentLinkedQueue, int start, int end,int count) {
        this.concurrentLinkedQueue = concurrentLinkedQueue;
        this.start = start;
        this.end = end;
        this.count=count;
    }

    @Override
    public Object call() throws Exception {
        for (int i=start;i<=end;i++){
            try {
                concurrentLinkedQueue.offer(new DataVo(i%count,String.valueOf(i)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
