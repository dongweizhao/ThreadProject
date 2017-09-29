package com.company.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by dongweizhao on 16/7/18.
 */
public class ReadWriteLock1 {
    static ReadWriteLock rwl = new ReentrantReadWriteLock();
    static String data = null;
    static volatile boolean validCheck = false;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    rwl.readLock().lock();
                    System.out.println(Thread.currentThread().getName() + "get 方法读加锁1完成");
                    try {
                        if (!validCheck) {
                            rwl.readLock().unlock();
                            System.out.println(Thread.currentThread().getName() + "get 方法读锁1解锁完成");
                            rwl.writeLock().lock();
                            System.out.println(Thread.currentThread().getName() + "get 方法写锁1加锁完成");
                            if (!validCheck) {
                                data = "aaa";
                                validCheck = true;
                            }
                            rwl.readLock().lock();
                            System.out.println(Thread.currentThread().getName() + "get 方法读锁锁加锁2完成");
                            rwl.writeLock().unlock();
                            System.out.println(Thread.currentThread().getName() + "get 方法写锁解锁1完成");

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        rwl.readLock().unlock();
                        System.out.println(Thread.currentThread().getName() + "get 方法读解锁2完成");
                    }
                }
            });
        }
    }


    public String get() {
        System.out.println(Thread.currentThread().getName() + "get 方法读加锁1完成");
        rwl.readLock().lock();
        try {
            if (!validCheck) {
                rwl.readLock().unlock();
                System.out.println(Thread.currentThread().getName() + "get 方法读锁1解锁完成");
                rwl.writeLock().lock();
                System.out.println(Thread.currentThread().getName() + "get 方法写锁1加锁完成");
                if (!validCheck) {
                    data = "aaa";
                    validCheck = true;
                }
                rwl.readLock().lock();
                System.out.println(Thread.currentThread().getName() + "get 方法读锁锁加锁2完成");
                rwl.writeLock().unlock();
                System.out.println(Thread.currentThread().getName() + "get 方法写锁解锁1完成");
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            rwl.readLock().unlock();
            System.out.println(Thread.currentThread().getName() + "get 方法读解锁2完成");
        }
    }
}
