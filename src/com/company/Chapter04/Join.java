package com.company.Chapter04;

/**
 * Created by dongweizhao on 16/11/15.
 */
public class Join {

    public static void main(String[] args) {
        Thread pre=Thread.currentThread();
        for (int i = 0; i <10 ; i++) {
            Thread t=new Thread(new Domino(pre),String.valueOf(i));
            t.start();
            pre=t;
        }
        System.out.println(Thread.currentThread().getName()+"  terminate");
    }
    static class Domino implements Runnable{
        Thread thread;

        public Domino(Thread thread) {
            this.thread = thread;
        }

        @Override
        public void run() {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName()+"  terminate");
        }
    }
}
