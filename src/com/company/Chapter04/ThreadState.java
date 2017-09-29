package com.company.Chapter04;

import java.util.concurrent.Executor;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by dongweizhao on 16/11/12.
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new TimeWariting(),"TimeWaritingThread").start();
        new Thread(new Waiting(),"WaitingThread").start();
        new Thread(new Blocked(),"BlockedThread-1").start();
        new Thread(new Blocked(),"BlockedThread-2").start();

    }
   static class TimeWariting implements Runnable{

        @Override
        public void run() {
            while (true){
                try {
                    SleepUtils.second(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class Waiting implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (Waiting.class){
                    try {
                        //Waiting.class.wait();
                        LockSupport.park();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
    static  class Blocked implements Runnable{

        @Override
        public void run() {
            while (true){
                synchronized (Blocked.class){
                    try {
                        SleepUtils.second(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
