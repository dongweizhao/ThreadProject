package com.company.Chapter05;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 自定义等待期
 * Created by dongweizhao on 16/12/5.
 */
public class CustCountdown {

    class CountSync extends AbstractQueuedSynchronizer {
        public CountSync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count not null");
            }
            setState(count);
        }

        @Override
        protected int tryAcquireShared(int arg) {
           /// System.out.println(Thread.currentThread().getName()+"   tryAcquireShared");
            return (getState() == 0) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (; ; ) {
                int current = getState();
                int next = current - arg;
                if (compareAndSetState(current, next)) {
                    return true;
                }
            }
        }
    }

    CountSync countSync = null;

    public CustCountdown(int count) {
        countSync = new CountSync(count);
    }

    public void await() {
        countSync.acquireShared(1);
    }

    public void countDown() {
        countSync.releaseShared(1);
    }
}
