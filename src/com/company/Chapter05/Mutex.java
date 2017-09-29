package com.company.Chapter05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 独占室锁
 * Created by dongweizhao on 16/11/23.
 */
public class Mutex implements Lock {

    private static class Sync extends AbstractQueuedSynchronizer {
        /**
         * 加锁
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryAcquire(int arg) {
//            for (;;){
//               boolean suc= compareAndSetState(0, 1);
//                if (suc)break;
//            }
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
            //return true;
        }

        /**
         * 解锁
         *
         * @param arg
         * @return
         */
        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) throw new IllegalArgumentException();

            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        /**
         * 是否处于占用状态
         *
         * @return
         */
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        Condition newCondition() {
            return new ConditionObject();
        }


    }

    Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    static int i = 0;
    static int j = 0;
    static CountDownLatch c1 = new CountDownLatch(10);
    static CountDownLatch c2 = new CountDownLatch(10);

    public static void main(String[] args) throws InterruptedException {
        Mutex mutex = new Mutex();
        // SpinLock mutex=new SpinLock();

        for (int k = 0; k < 10; k++) {
            Thread t = new Thread(new SyncRunnder(mutex));
            t.start();
        }
        for (int k = 0; k < 10; k++) {
            Thread t = new Thread(new Runnder());
            t.start();
        }

        c1.await();
        c2.await();
        System.out.println("i = [" + i + "]  j=[" + j + "]");
    }

    private static class SyncRunnder implements Runnable {
        Mutex mutex;

        public SyncRunnder(Mutex mutex) {
            this.mutex = mutex;
        }

        @Override
        public void run() {
            mutex.lock();
            for (int n = 0; n < 10000; n++) {
                i++;
            }
            mutex.unlock();
            c1.countDown();
        }
    }

    private static class Runnder implements Runnable {

        @Override
        public void run() {
            for (int n = 0; n < 10000; n++) {
                j++;
            }
            c2.countDown();


        }
    }
}
