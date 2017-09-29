package com.company.Chapter04;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by dongweizhao on 17/9/21.
 */
public class MyTest {
    @Test
    public void test1() {
        BlockingQueue<Runnable> q = new LinkedBlockingDeque<>();
        q.add(new TaskRunable1());
        q.add(new TaskRunable1());
        q.add(new TaskRunable1());
        List<Runnable> taskList = new ArrayList<Runnable>();
        q.drainTo(taskList);
        System.out.println("taskList:" + taskList.size());
        System.out.println("q:" + q.size());
        System.out.println("q.isEmpty:" + q.isEmpty());
        String[] runnables = new String[1];
        runnables = (String[]) java.lang.reflect.Array.newInstance(
                runnables.getClass().getComponentType(), 3);
        System.out.println("runnables.length:" + runnables.length);
    }

    @Test
    public void test2() {
        BlockingQueue<TaskRunable> q = new DelayQueue<>();
        q.add(new TaskRunable(System.currentTimeMillis()));
        q.add(new TaskRunable(System.currentTimeMillis()+50000));
        q.add(new TaskRunable(System.currentTimeMillis()+3000));
        List<Runnable> taskList = new ArrayList<Runnable>();
        q.drainTo(taskList);
        System.out.println("taskList:" + taskList.size());
        System.out.println("q:" + q.size());
        System.out.println("q.isEmpty:" + q.isEmpty());
        String[] runnables = new String[1];
        runnables = (String[]) java.lang.reflect.Array.newInstance(
                runnables.getClass().getComponentType(), 3);
        System.out.println("runnables.length:" + runnables.length);
    }
    @Test
    public void test3(){
        long convert = TimeUnit.SECONDS.convert(4000, TimeUnit.MILLISECONDS);
        System.out.println(convert);

    }
    class TaskRunable1 implements Runnable {

        @Override
        public void run() {
            System.out.println("H");
        }

    }

    class TaskRunable implements Runnable, Delayed {

        public long time;

        public long now() {
            return System.currentTimeMillis();
        }

        public TaskRunable(long time) {
            this.time = time;
        }

        @Override
        public void run() {
            System.out.println("H");
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time - now(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            if (o == this) return 0;
            if (o instanceof TaskRunable) {
                TaskRunable x = (TaskRunable) o;
                long diff = time - x.time;
                if (diff > 0) {
                    return 1;
                } else if (diff < 0) {
                    return -1;
                } else {
                    return 0;
                }
            }
            long d = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            return d == 0 ? 0 : d > 0 ? 1 : -1;
        }
    }
}
