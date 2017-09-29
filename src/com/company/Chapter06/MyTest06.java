package com.company.Chapter06;

import org.junit.Test;

import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by dongweizhao on 17/5/21.
 */
public class MyTest06 {
    /**
     * 测试hashMap的死循环
     */
    @Test
    public void test1() throws InterruptedException {
      final   HashMap<String,String> hashMap=new HashMap<String, String>(2);
        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {

                for ( int i = 0; i <10000 ; i++) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("i:");
                            hashMap.put(UUID.randomUUID().toString(),"");
                        }
                    },"ftf"+i).start();
                }
            }
        },"ftf");
        t.start();
        t.join();
    }
    @Test
    public void test2(){
        HashMap<String,String> h=new HashMap<String, String>(5);
        h.put("2","222");
        h.put("2","111");
        System.out.println(h.get("2"));
    }
    @Test
    public void test3(){
        System.out.println(1 << 30);
    }
    com.company.Chapter06.HashMap.Entry[] table=new HashMap.Entry[2];

    @Test
    public void test4() throws InterruptedException {

        //HashMap.Entry C=new HashMap.Entry(2,"5","C",null);
        HashMap.Entry B=new HashMap.Entry(2,"7","B",null);
        table[1]=new HashMap.Entry<String,String>(2, "3", "A", B);//A-B-C =3>7>5
        final Lock lock=new ReentrantLock();
        final Condition condition=lock.newCondition();

         Thread t1=new Thread(new Runnable() {
             @Override
             public void run() {
                 try {
                     final com.company.Chapter06.HashMap.Entry[] newTable=new HashMap.Entry[4];
                     transfer(newTable,true,lock,condition);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });
        t1.start();
        TimeUnit.SECONDS.sleep(1);
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final com.company.Chapter06.HashMap.Entry[] newTable=new HashMap.Entry[4];
                    transfer2(newTable,true,lock,condition);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        t2.start();
        t1.join();
        //newTable[2]=B>A=7>3
        //newTable[0]=C=5
    }

    void transfer(HashMap.Entry[] newTable, boolean rehash,Lock lock,Condition condition) throws InterruptedException {
        try {
            lock.lock();
            int newCapacity = newTable.length;
            int j=0;
            for (HashMap.Entry e : table) {
                while(null != e) {
                    HashMap.Entry next = e.next;
                    if (j==0){
                        condition.await();
                    }
                    if (rehash) {
                        //e.hash = null == e.key ? 0 : HashMap.hash(e.key);
                        e.hash=49;
                    }
                    //int i = HashMap.indexFor(e.hash, newCapacity);
                    int i=3;
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                    j++;
                }
            }
        }finally {
            lock.unlock();
        }

    }
    void transfer2(HashMap.Entry[] newTable, boolean rehash,Lock lock,Condition condition) {
        try {
            lock.lock();
            int newCapacity = newTable.length;
            for (HashMap.Entry e : table) {
                while(null != e) {
                    HashMap.Entry next = e.next;
                    if (rehash) {
                      //  e.hash = null == e.key ? 0 : HashMap.hash(e.key);
                        e.hash=49;
                    }
                    int i = 3;
                    e.next = newTable[i];
                    newTable[i] = e;
                    e = next;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            condition.signal();
            lock.unlock();
        }

    }
    @Test
    public void test5(){
        HashMap.Entry e=new HashMap.Entry(2,"7","B",null);
        HashMap.Entry next = e.next;
        System.out.println("next:"+next);
    }
    @Test
    public void test6(){
        P p=new P();
        System.out.println("P.name:"+p.name);
        change(p);
        System.out.println("P.name:"+p.name);
    }

    @Test
    public void test7(){
        P tail=new P();
        P n=new P();
        P t=tail;
        if (tail==t){
            tail=n;
        }

        System.out.println("t==tail:"+(t==tail));
        System.out.println("tail==n:"+(tail==n));
    }
    @Test
    public void test8() throws InterruptedException {
        final AtomicStampedReference atomicStampedReference=new AtomicStampedReference(100,0);
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                atomicStampedReference.compareAndSet(100,101,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
                atomicStampedReference.compareAndSet(101,100,atomicStampedReference.getStamp(),atomicStampedReference.getStamp()+1);
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int stamp=atomicStampedReference.getStamp();
                    System.out.println("121212");
                    TimeUnit.SECONDS.sleep(1);

                    boolean b=atomicStampedReference.compareAndSet(100,101,stamp,stamp+1);
                    System.out.println("b:"+b);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        t1.start();
        t2.start();
        t2.join();
        System.out.println(atomicStampedReference.getReference());
    }
    @Test
    public void test9(){
        for (;;){
            System.out.println(UUID.randomUUID().toString());
            System.out.println(UUID.randomUUID().toString());
            System.out.println(UUID.randomUUID().toString());
            System.out.println(UUID.randomUUID().toString());
        }
    }
    public class User{
        private int old;
        private String version;

        public int getOld() {
            return old;
        }

        public void setOld(int old) {
            this.old = old;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }



    public void change(P p){
        p.name="2";
    }

      class P{
        String name="张三";
    }
}
