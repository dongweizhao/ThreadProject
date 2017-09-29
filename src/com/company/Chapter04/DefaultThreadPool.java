package com.company.Chapter04;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 默认线程池
 * Created by dongweizhao on 16/11/20.
 */
public class DefaultThreadPool<Job extends Runnable> implements TheadPool<Job> {
    //最大容量
    private int MAX_NUM = 10;
    //最小容量
    private int MIN_NUM = 1;
    //工作集合
    public final LinkedList<Job> jobs = new LinkedList<Job>();

    List<Worker> works = Collections.synchronizedList(new ArrayList<Worker>());

    AtomicInteger threadName = new AtomicInteger();

    public DefaultThreadPool() {
        init(MAX_NUM);
    }

    public DefaultThreadPool(int num) {
        num = num > MAX_NUM ? MAX_NUM : num < MIN_NUM ? MIN_NUM : num;
        init(num);
    }


    private void init(int num) {
        for (int i = 0; i < num; i++) {
            Worker work = new Worker();
            Thread t = new Thread(work, "Thread-Worker-" + threadName.incrementAndGet());
            t.start();
        }
    }

    @Override
    public void execute(Job job) {
        if (job != null) {
            synchronized (jobs) {
                jobs.add(job);
                jobs.notify();
            }
        }
    }

    public void shutdown() {
        for (Worker work : works) {
            work.shutdown();
        }
    }

    @Override
    public int addWorker(int num) {
        return 0;
    }

    @Override
    public int removeWorker(int num) {
        return 0;
    }

    @Override
    public int getWaitSize() {
        return jobs.size();
    }

    class Worker implements Runnable {
        volatile boolean runing = true;

        @Override
        public void run() {
            Job job = null;
            while (runing) {
                synchronized (jobs) {
                    while (jobs.isEmpty()) {
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if (job != null) {
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            runing = false;
        }
    }

    public static void main(String[] args) {
        TheadPool theadPool = new DefaultThreadPool(2);
        theadPool.execute(new Job2());
    }

    static class Job2 implements Runnable {

        @Override
        public void run() {
            System.out.println("my name is job1");
        }
    }
}
