package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 使用AtomicBoolean,实现同步安全类自旋锁控制
 * Created by dongweizhao on 16/11/9.
 */
public class SpinLock {
    AtomicBoolean atomicBoolean = new AtomicBoolean(false);
    volatile  boolean flag=false;
    public void lock(){
        for (; ; ) {
            boolean suc = atomicBoolean.compareAndSet(false, true);
            //如果是false,就继续循环等待;
            if (suc) break;
        }
    }



    public void unlock(){
        atomicBoolean.compareAndSet(true, false);
    }

    public static void main(String[] args) throws InterruptedException {
        final SpinLockTest cas = new SpinLockTest(new SpinLock());
        List<Thread> ts = new ArrayList<Thread>();
        for (int i = 0; i < 100; i++) {
            ts.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 1000; j++) {
                        cas.add();
                        cas.atomicAdd();
                    }

                }
            }));
        }
        for (Thread t : ts) {
            t.start();
        }
        for (Thread t : ts) {
            t.join();
        }
        System.out.println("i:" + cas.i);
        System.out.println("j:" + cas.j);

    }



   //自旋锁
   static class SpinLockTest{
       int i=0,j=0;
       private SpinLock spinLock;

       public SpinLockTest(SpinLock spinLock) {
           this.spinLock=spinLock;
       }

       public void atomicAdd() {
           spinLock.lock();
           //同步代码块
           i++;
           //System.out.println(Thread.currentThread().getName() + "  i:" + i);
           spinLock.unlock();
       }

       public void add() {
           j++;
       }
   }
}
