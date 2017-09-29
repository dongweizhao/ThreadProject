package com.company.Chapter05;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**并发访问控制器
 * Created by dongweizhao on 17/4/13.
 */
public class TwisLock implements Lock{
    Sync sync=new Sync(2);
    @Override
    public void lock() {
        sync.acquireShared(1);
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
    public class  Sync extends AbstractQueuedSynchronizer{
        //初始化控制线程的数量
        public Sync(int state) {
            setState(state);
        }

        protected int tryAcquireShared(int arg) {
            if (arg>0){
              //  for (;;){
                    int count= getState()-1;
                    if (count<0||compareAndSetState(getState(),count)){
                        return count;
                    }
                //}

            }
            throw new IllegalArgumentException("参数必须大于0");
        }

        protected Sync() {
            super();
        }


        protected boolean tryReleaseShared(int arg) {
            if (arg>0){
                //此处加死循环,是为了避免,并发线程同时去获取getstate,只能有一个线程获取成功,如果只有一个线程获取成功,其他获取同步状态失败线程返回,
                // 那么会导致同步失败的线程,流程已走完,但是锁没释放,导致其它等待获取锁的线程,一直在等待。
                for (;;){
                    int count= getState()+1;
                    if (compareAndSetState(getState(),count)){
                        return true;
                    }
                }
            }
            throw new IllegalArgumentException("参数必须大于0");
        }
    }

    public static void main(String[] args) {
        final CountDownLatch countdown=new CountDownLatch(1);
        final TwisLock twisLock=new TwisLock();
        for (int i = 0; i <10 ; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread Name:"+Thread.currentThread().getName()+"请求进入");
                        countdown.await();//此处增加await是为了同步等待,等所有线程创建完成,同时去获取锁
                        twisLock.lock();
                        System.out.println("Thread Name:"+Thread.currentThread().getName()+"获取锁");
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println("Thread Name:"+Thread.currentThread().getName()+"释放锁");
                        twisLock.unlock();
                    }
                }
            },"Thread-Name-"+i).start();
        }
        countdown.countDown();
    }
}
