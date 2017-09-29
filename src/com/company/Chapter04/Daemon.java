package com.company.Chapter04;

/**
 * Created by dongweizhao on 16/11/12.
 */
public class Daemon {
    public static void main(String[] args) {
        Thread thread=new Thread(new DaemonRunner(),"DaemonRunner");
        thread.setDaemon(true);
        thread.start();
    }
    static class DaemonRunner implements Runnable{

        @Override
        public void run() {
            try {
                System.out.println("DaemonThread finally try.");
                SleepUtils.second(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}
