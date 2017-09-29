package com.company.Chapter03;

import sun.org.mozilla.javascript.internal.RhinoException;

/**
 * volatitle原子性测试
 * Created by dongweizhao on 16/11/12.
 */
public class VolatileFeaturesExapmple {
     long v1=0L;
    public void set(long l){
        v1=l;
    }
    public void getAndIncrement(){
        v1++;
    }
    public long get(){
        return v1;
    }

    public static void main(String[] args) throws InterruptedException {
        final VolatileFeaturesExapmple volatileFeaturesExapmple=new VolatileFeaturesExapmple();
        for (int i = 0; i <10000000 ; i++) {
            final int finalI = i;
            Thread set= new Thread(new Runnable() {
               @Override
               public void run() {
                   volatileFeaturesExapmple.set(finalI);
                   System.out.println(Thread.currentThread().getName()+"写 vl:"+finalI);

               }
           });
            Thread get=new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName()+"读 vl:"+volatileFeaturesExapmple.get());
                }
            });

            set.start();
            get.start();

        }
    }
}
