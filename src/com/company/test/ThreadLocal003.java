package com.company.test;

import java.util.Random;

/**
 * Created by dongweizhao on 16/7/14.
 */
public class ThreadLocal003 {
    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                   int data = new Random().nextInt();
                    ThreadScopeData.getInstance().setName("name:" + data);
                    ThreadScopeData.getInstance().setAge(data);
                    System.out.println(Thread.currentThread().getName() + " from data:" + data);
                    new A().a();
                    new B().b();
                }
            }).start();
        }
    }

    static class A {
        public void a() {
            System.out.println("A sub Thread:" + Thread.currentThread().getName() + " from data:" +ThreadScopeData.getInstance().getName()+"  age:"+ThreadScopeData.getInstance().getAge());
        }
    }

    static class B {
        public void b() {
            System.out.println("B sub Thread:" + Thread.currentThread().getName() + " from name:" +ThreadScopeData.getInstance().getName()+"  age:"+ThreadScopeData.getInstance().getAge());
        }
    }

    static class ThreadScopeData {
        private String name;
        private int age;

        private ThreadScopeData() {
        }

        public static ThreadScopeData getInstance() {
            install = map.get();
            if (install == null) {
                install = new ThreadScopeData();
                map.set(install);
            }
            return install;
        }

        private static ThreadScopeData install;
        private static ThreadLocal<ThreadScopeData> map = new ThreadLocal<ThreadScopeData>();

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

