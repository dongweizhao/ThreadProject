package com.company;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 16/7/3.
 */
public class Foo {
    public int x = 100;

    private Condition condition;
    private Lock banklock = new ReentrantLock();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void add(int count) {
        x = x + count;
    }

    public int fix(int y) {
        banklock.lock();
        try {

            if (x < y) {
                condition.await();
            } else {
                x = x + y;
                condition.signalAll();
            }
            x = x - y;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            banklock.unlock();
        }
        return x;
    }

    public Foo() {
    }
}
