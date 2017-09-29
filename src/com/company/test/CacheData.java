package com.company.test;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by dongweizhao on 16/7/17.
 */
public class CacheData {
    public static void main(String[] args) {
        CacheData cacheData=new CacheData();
        System.out.println("args = [" + cacheData.get() + "]");
    }

    ReadWriteLock rwl = new ReentrantReadWriteLock();
    String data = null;
    private volatile boolean cacheValid = false;

    public Object get() {
        rwl.readLock().lock();
        try {
            if (!cacheValid) {
                rwl.readLock().unlock();
                rwl.writeLock().lock();
                if (!cacheValid) {//当并发线程获取时，第一次判断已经满足，但是第一个线程写完值以后，另一个等待线程有进来写，所以进行了二次判断
                    data = "aaa";//获取数据库数据
                    cacheValid = true;
                }
                rwl.readLock().lock();//在释放写锁之前，先获得读锁，因为如果先释放写锁，在获取读锁时，机会有可能被另一个写线程获取，所以要等待下一次机会才能获取到锁
                rwl.writeLock().unlock();
            }
            return data;
        } finally {
            rwl.readLock().unlock();
        }
    }
}
