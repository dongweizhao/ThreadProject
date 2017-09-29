package com.company.Chapter04;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 16/11/12.
 */
public class Priority {
    private static volatile boolean notStart = true;
    private static volatile boolean noEnd = true;

    public static void main(String[] args) throws InterruptedException {
        List<Job> jobList = new ArrayList<Job>();
        for (int i = 0; i < 10; i++) {
            int priority = i < 5 ? Thread.MIN_PRIORITY : Thread.MAX_PRIORITY;
            Job job = new Job(priority);
            jobList.add(job);
            Thread t = new Thread(job, "Thread " + i);
            t.setPriority(priority);
            t.start();
        }
        notStart = false;
        TimeUnit.SECONDS.sleep(10);
        noEnd = false;
        for (Job job : jobList) {
            System.out.println("Job priotity:" + job.priority + "   job jobcount:" + job.jobCount);
        }
    }

    static class Job implements Runnable {
        private int priority;
        private int jobCount;

        public Job(int priority) {
            this.priority = priority;
        }

        @Override
        public void run() {
            while (notStart) {
                Thread.yield();
            }
            while (noEnd) {
                Thread.yield();
                jobCount++;
            }
        }
    }
}
