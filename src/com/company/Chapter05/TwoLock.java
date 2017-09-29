package com.company.Chapter05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 允许并发2个线程的共享锁
 * Created by dongweizhao on 16/12/5.
 */
public class TwoLock implements Lock {
    private final Sync sync = new Sync(2);

    public TwoLock() throws Exception {
    }

    class Sync extends AbstractQueuedSynchronizer {
        public Sync(int count) throws Exception {
            if (count <= 0) {
                throw new IllegalArgumentException("count not null");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int count) {
            for (; ; ) {
                int current = getState();
                int nextCurrent = current - count;
                if (nextCurrent < 0 || compareAndSetState(current, nextCurrent)) {
                    return nextCurrent;
                }
            }
        }

        @Override
        protected boolean tryReleaseShared(int count) {
            for (; ; ) {
                int current = getState();
                int totalCurrent = current + count;
                if (compareAndSetState(current, totalCurrent)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.acquireShared(1);
        //System.out.println(Thread.currentThread().getName() + "获取锁");
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public void unlock() {
        sync.releaseShared(1);
    }

    @Override
    public Condition newCondition() {
        return null;
    }


    public static void main(String[] args) throws Exception {
        final Lock lock = new TwoLock();
        final CustCountdown countdown = new CustCountdown(1);
        class Worker extends Thread {
            public Worker(String name) {
                super(name);
            }

            @Override
            public void run() {
                countdown.await();
                int i = 1;
               // while (true) {

                System.out.println("thread-name:" + Thread.currentThread().getName()+"开始获取锁");
                    lock.lock();
                System.out.println("thread-name:" + Thread.currentThread().getName()+"已获取锁");
                    //System.out.println(Thread.currentThread().getName() + "第" + i + "次循环");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        //TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.println("thread-name:" + Thread.currentThread().getName()+"释放锁");
                        lock.unlock();

                    }
                //}

            }
        }
        for (int i = 0; i < 10; i++) {
            Worker woker = new Worker("Thread-name-" + i);
            woker.start();
        }
        countdown.countDown();
    }


}
