package com.company.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by dongweizhao on 16/7/14.
 */
public class Test002 {
    static int data = 0;
    static Map<Thread, Integer> threadMap = new HashMap<Thread, Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // System.out.println("start"+ Thread.currentThread().getName() + "-----"+new Random().nextInt());
                    data = new Random().nextInt();
                    threadMap.put(Thread.currentThread(), data);
                    System.out.println(Thread.currentThread().getName() + " from data:" + data);
                    System.out.println(Thread.currentThread().getName() + "-----");
                    new A().a();
                    new B().b();
                }
            }).start();
        }
    }

    static final class A {
        public void a() {
            System.out.println("A sub Thread:" + Thread.currentThread().getName() + " from data:" + threadMap.get(Thread.currentThread()));
        }
      static final  class A1{
          static {
              System.out.println("new Contruntor A1");
          }
      }
    }

    static class B {
        public void b() {
            System.out.println("B sub Thread:" + Thread.currentThread().getName() + " from data:" + threadMap.get(Thread.currentThread()));
        }
    }
}
