package com.company.test;

import java.util.concurrent.locks.ReentrantLock;

/**非公平锁
 * Created by dongweizhao on 16/10/21.
 */
public class NoFairLock {
    volatile int count=0;

     public void fair(boolean fair){
         //final ReentrantLock reentrantLock=new ReentrantLock(fair);
         final ReentrantLock reentrantLock=new ReentrantLock();
         long startTime=System.currentTimeMillis();
         System.out.println("start:"+startTime);

         for (int i = 0; i <10 ; i++) {
             new Thread(new Runnable() {
                 @Override
                 public void run() {
                     reentrantLock.lock();
                     System.out.println(Thread.currentThread().getName()+"执行了.....");
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     count=count+1;
                     reentrantLock.unlock();
                 }
             }).start();
         }

         for (;;){
             if (count==10){
                 long endTime=System.currentTimeMillis();
                 System.out.println("end:"+endTime);
                 System.out.println("总耗时:"+(endTime-startTime));
                 break;
             }
         }

     }


     public void signal(){
         long startTime=System.currentTimeMillis();
         System.out.println("start:"+startTime);

         for (int i = 0; i <10 ; i++) {
                     System.out.println(Thread.currentThread().getName()+"执行了.....");
                     try {
                         Thread.sleep(1000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
                     count=count+1;
                 }

                 long endTime=System.currentTimeMillis();
                 System.out.println("end:"+endTime);
                 System.out.println("总耗时:"+(endTime-startTime));

     }
    public static void main(String[] args) {
     NoFairLock noFair=new NoFairLock();
        noFair.fair(true);
       // noFair.fair(false);
        //noFair.signal();
    }
}
