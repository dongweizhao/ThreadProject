package com.company;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 16/7/4.
 */
public class Bank {
    private double[] accouts;

    public synchronized void transfer(int from, int to, int amount) throws InterruptedException {
        while (accouts[from] < amount)
            wait();
        accouts[from] -= amount;
        accouts[to] += amount;
        notifyAll();
    }
    public synchronized void add() throws InterruptedException {
        System.out.println("add");
        TimeUnit.SECONDS.sleep(10);
        System.out.println("add after");
    }
    public synchronized  void jian(){
        System.out.println("jian");
    }

    public static void main(String[] args) {
        final Bank bank=new Bank();
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    bank.add();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread thread2=new Thread(new Runnable() {
            @Override
            public void run() {
                bank.jian();
            }
        });

        thread.start();
        thread2.start();
    }


}
