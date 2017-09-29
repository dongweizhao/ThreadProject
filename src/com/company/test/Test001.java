package com.company.test;

/**
 * Created by dongweizhao on 16/7/14.
 */
public class Test001 {
    public static void main(String[] args) throws InterruptedException {
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    try {
                        business.sub(i);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        for (int i = 0; i < 50; i++) {
            business.main(i);
        }
    }
}

class Business {
    boolean flag = false;

    public synchronized void sub(int i) throws InterruptedException {
        if (!flag) {
            this.wait();
        }
        for (int j = 1; j < 11; j++) {
            System.out.println("sub loop j=[" + j + "] i = [" + i + "]");
        }
        flag = false;
        this.notify();
    }

    public synchronized void main(int i) throws InterruptedException {
        if (flag) {
            this.wait();
        }
        for (int j = 1; j < 101; j++) {
            System.out.println("main loop j=[" + j + "] i = [" + i + "]");
        }
        flag = true;
        this.notify();
    }
}
