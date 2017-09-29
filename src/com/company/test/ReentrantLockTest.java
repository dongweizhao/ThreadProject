package com.company.test;

import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 16/10/21.
 */
public class ReentrantLockTest {
    private static Lock fairLock = new ReentrantLock2(true);

    private static Lock unfairLock = new ReentrantLock2();

    @Test
    public void fair() {
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(fairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        // sleep 5000ms
    }

    @Test
    public void unfair() {
        System.out.println("unfair version");
        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread(new Job(unfairLock)) {
                public String toString() {
                    return getName();
                }
            };
            thread.setName("" + i);
            thread.start();
        }
        // sleep 5000ms
    }



    private static class Job implements Runnable{
      private Lock lock;

      public Job(Lock lock) {
          this.lock = lock;
      }

      @Override
      public void run() {
          for (int i = 0; i <5 ; i++) {
              lock.lock();
              try {
                  System.out.println("Lock by:"
                          + Thread.currentThread().getName() + " and "
                          + ((ReentrantLock2)lock).getQueuedThreads()+ " waits.");
              }finally {
                  lock.unlock();
              }
          }

      }
  }

    private static class ReentrantLock2 extends ReentrantLock{
        public ReentrantLock2() {
        }

        public ReentrantLock2(boolean fair) {
            super(fair);
        }

        @Override
        public Collection<Thread> getQueuedThreads() {
            return super.getQueuedThreads();
        }
    }
}
