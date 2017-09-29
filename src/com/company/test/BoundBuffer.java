package com.company.test;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 16/7/18.
 * 阻塞队列
 */
public class BoundBuffer {
    private Object[] items = new Object[100];
    Lock lock = new ReentrantLock();
    Condition notEmpty = lock.newCondition();
    Condition notFull = lock.newCondition();
    int count, putIndex, getIndex;

    public static void main(String[] args) {
        BoundBuffer buffer=new BoundBuffer();
    }

   public void put(Object val) {
        lock.lock();
        try {
            while (count == items.length)
                notFull.wait();
            items[putIndex] = val;
            if (++putIndex == items.length) putIndex = 0;
            count++;
            notEmpty.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public  Object get() {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.wait();
            }
            Object x = items[getIndex];
            if (++getIndex == items.length) getIndex = 0;
            count--;
            notFull.signal();
            return x;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }


}
