package com.company.test;


import com.company.Chapter04.TheadPool;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by dongweizhao on 17/2/21.
 */
public class MyTest {
    @Test
    public void test1() throws InterruptedException {
        try {
            Process process = Runtime.getRuntime().exec("sh test.sh");
            int c = process.waitFor();
            if (c != 0) {
                System.out.println("执行失败");
            } else {
                System.out.println("执行成功");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() throws InterruptedException {
        System.out.println("开始时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        Thread.sleep(1000L);
        System.out.println("结束时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        ThreadLocal<List> threadLocal = new ThreadLocal<List>();
        List list = new ArrayList();
        list.add("del1");
        threadLocal.set(list);
        List list1 = threadLocal.get();
        list1.add("del2");
        System.out.println(threadLocal.get());
    }

    @Test
    public void test3() {
        int j = 11;
        j >>= 2;
        //把数字11向右移2位,相当于11除以2的2次方,取整为2
        System.out.println("j:" + j);

        j = 24;
        j >>= 3;
        ////把数字24向右移3位,相当于24除以2的3次方,取整为8
        System.out.println("j:" + j);


        long n = 20;
        System.out.println("LONG左移前二进制值:" + Long.toBinaryString(n));
        n <<= 2;
        System.out.println("LONG左移后二进制值:" + Long.toBinaryString(n));
        ////把数字20向左移2位,相当于20乘以2的2次方,为80
        System.out.println("LONG 左移后十进制值:" + n);

        short k = 20;
        System.out.println("short左移前二进制值:" + Integer.toBinaryString(k));
        k <<= 2;
        System.out.println("short左移后二进制值:" + Integer.toBinaryString(k));
        ////把数字20向左移2位,相当于20乘以2的2次方,为80
        System.out.println("short 左移后十进制值:" + k);

        byte o = 20;
        System.out.println("byte左移前二进制值:" + Integer.toBinaryString(o));
        o <<= 2;
        System.out.println("byte左移后二进制值:" + Integer.toBinaryString(o));
        ////把数字20向左移2位,相当于20乘以2的2次方,为80
        System.out.println("byte 左移后十进制值:" + o);


        j = 20;
        System.out.println("左移前二进制值:" + Integer.toBinaryString(j));
        j <<= 2;
        System.out.println("左移后二进制值:" + Integer.toBinaryString(j));
        ////把数字20向左移2位,相当于20乘以2的2次方,为80
        System.out.println("左移后十进制值:" + j);


        j = 15;

        System.out.println("右移前二进制值:" + Integer.toBinaryString(j));
        j >>= 2;
        System.out.println("右移后二进制值:" + Integer.toBinaryString(j));
        ////把数字20向右移2位,相当于20除以2的2次方,为5
        System.out.println("右移后十进制值:" + j);


        j = -15;

        System.out.println("右移前二进制值:" + Integer.toBinaryString(j));
        j >>= 2;
        System.out.println("右移后二进制值:" + Integer.toBinaryString(j));
        ////把数字20向右移2位,相当于20除以2的2次方,为5
        System.out.println("右移后十进制值:" + j);

        j = -20;

        j = 20;

        System.out.println("无符号右移前二进制值:" + Integer.toBinaryString(j));
        j >>>= 3;
        System.out.println("无符号右移后二进制值:" + Integer.toBinaryString(j));
        ////把数字20向右移2位,相当于20除以2的2次方,为5
        System.out.println("无符号右移后十进制值:" + j);

        j = -20;
        System.out.println("无符号右移前二进制值:" + Integer.toBinaryString(j));
        j >>>= 3;
        System.out.println("无符号右移后二进制值:" + Integer.toBinaryString(j));
        ////把数字20向右移2位,相当于20除以2的2次方,为5
        System.out.println("无符号右移后十进制值:" + j);


        int DEFAULT_LEN = 1 << 30;

        //System.out.println("default_len:"+DEFAULT_LEN);

        long num = 100;
        //System.out.println("二进制数据-num:"+Long.toBinaryString(num));
        //long n=num >>> 1;
        //System.out.println("二进制数据-n:"+Long.toBinaryString(n));
    }

    @Test
    public void test4() {
        for (int i = 0; i < 10; i++) {
            //判断偶数 两个位都为1时，结果才为1
            if ((i & 1) == 0) {
                System.out.println("计算后的值:" + Integer.toBinaryString(i & 1));
                System.out.println("i=" + i);
            }
        }
    }

    @Test
    public void test5() {
        int a = 20;
        int b = 12;
        System.out.println("二进制数据a:" + Integer.toBinaryString(a) + "二进制数据b:" + Integer.toBinaryString(b));
        int c = a & b;
        //& 相对应位都是1,则为1,否则为0
        System.out.println("十进制数据c:" + c + "二进制数据c:" + Integer.toBinaryString(c));
        System.out.println("--------------------------------");
        a = 20;
        b = 12;
        c = a | b;
        //| 相对应位都是0,则为0,否则为1
        System.out.println("十进制数据c:" + c + "二进制数据c:" + Integer.toBinaryString(c));

        System.out.println("--------------------------------");
        a = 20;
        b = 12;
        c = a ^ b;
        //^ 相对应位相同,则为0,否则为1
        System.out.println("十进制数据c:" + c + "二进制数据c:" + Integer.toBinaryString(c));

        System.out.println("--------------------------------");
        a = 20;
        c = ~a;
        //~ 按位补操作符,反转操作每一位,即1为0,0为1
        System.out.println("十进制数据c:" + c + "二进制数据c:" + Integer.toBinaryString(c));
        int A = 60;
        System.out.println("二进制数据A:" + Integer.toBinaryString(A) + "二进制数据13:" + Integer.toBinaryString(13));
        A &= 13;

        System.out.println("十进制数据A:" + A + "二进制数据A:" + Integer.toBinaryString(A));

        System.out.println("--------------------------------");

        A = 60;
        System.out.println("二进制数据A:" + Integer.toBinaryString(A) + "二进制数据13:" + Integer.toBinaryString(13));
        A |= 13;

        System.out.println("十进制数据A:" + A + "二进制数据A:" + Integer.toBinaryString(A));

        System.out.println("--------------------------------");
        A = 60;
        System.out.println("二进制数据A:" + Integer.toBinaryString(A) + "二进制数据13:" + Integer.toBinaryString(13));
        A ^= 13;

        System.out.println("十进制数据A:" + A + "二进制数据A:" + Integer.toBinaryString(A));

    }

    //异或
    @Test
    public void test6() {

        int a = 10;//1010
        int b = 12;//1100
        a ^= b;//110
        b ^= a;//1010
        a ^= b;//1100
        System.out.println("a=" + a + " b=" + b);
        //a=12 b=10
    }


    //非~求绝对值
    @Test
    public void test7() {
        int a = -10;
        //向右移31位,即 i=0为正数,i=-1为负数
        int i = a >> 31;
        //因为正数的绝对值为正数,所以如果为0则直接返回,负数需要进行反转+1
        //System.out.println(i==0?a:(~a+1));

        System.out.println("二进制a:" + Integer.toBinaryString(a));
        //11111111111111111111111111110110
        System.out.println("二进制a:" + Integer.toBinaryString(i));
        //11111111111111111111111111111111
        System.out.println("a^-1二进制:" + Integer.toBinaryString(a ^ -1));
        //负数与-1进行异或相等于使用~进行反转
        System.out.println("a^-1:" + ((a ^ i) - i));

    }

    @Test
    public void test8() {
        int bData = 0;
        //存储数值1
        bData = bData | (1 << (2 - 1));
        //存储数值0
        bData = bData & (~(1 << (2 - 1)));
        //读取数据
        int n = bData & (1 << (2 - 1));

        System.out.println("n:" + n);
    }

    @Test
    public void test9() {
        int s = 0;
        System.out.println("s>>>16:" + (s >>> 16));
        System.out.println("1<<16十进制:" + (1 << 16));
        System.out.println("1<<16:" + Integer.toBinaryString(1 << 16));
        s = s + (1 << 16);
        System.out.println("s:" + s);
        System.out.println("s>>>16:" + (s >>> 16));
        System.out.println("s>>>16二进制:" + Integer.toBinaryString(s >>> 16));
        s = s + 65536;
        System.out.println("s二进制:" + Integer.toBinaryString(s));
        System.out.println("s>>>16:" + (s >>> 16));
        System.out.println("s>>>16二进制:" + Integer.toBinaryString(s >>> 16));
        s = s + 65536;
        System.out.println("s>>>16:" + (s >>> 16));
        System.out.println("s>>>16二进制:" + Integer.toBinaryString(s >>> 16));
        System.out.println("s二进制:" + Integer.toBinaryString(s));
        if (10 < '\uffff') {
            System.out.println("小于:\uffff");
        }
    }

    @Test
    public void test10() {
        int s = 65536;
        System.out.println("原始值二进制:" + Integer.toBinaryString(s));
        s = s + 1;
        System.out.println("原始值二进制+1:" + Integer.toBinaryString(s));
    }

    //溢出和下溢测试
    @Test
    public void test11() {
        int s = -2147483648;
        System.out.println("s二进制:" + Integer.toBinaryString(s));
        System.out.println("s:" + s);
        s += 1;
        System.out.println("s二进制:" + Integer.toBinaryString(s));
        System.out.println("s:" + s);

    }

    @Test
    public void test12() {

       // retry:// 1<span style="font-family: Arial, Helvetica, sans-serif;">（行2）</span>
        for (int i = 0; i < 10; i++) {
            retry:// 2（行4）
            while (i == 5) {
                continue retry;
            }
            System.out.print(i + " ");
        }
    }
    @Test
    public void test13() {
        ConcurrentHashMap concurrentHashMap=new ConcurrentHashMap();
        Object obj=concurrentHashMap.putIfAbsent("1","2");
        System.out.println(obj);
        int result=1;
        result=+1;
        result=+1;
        System.out.println(result);
    }
    private static int ctlOf(int rs, int wc) { return rs | wc; }
    @Test
    public void test14(){
        System.out.println(Integer.SIZE);
        int count=-1<<29;
        System.out.println(Integer.toBinaryString(count));
        AtomicInteger ctl = new AtomicInteger(ctlOf(count, 0));
        System.out.println(Integer.toBinaryString(ctl.get()));
        System.out.println(Integer.toBinaryString((2<< 29)));
        System.out.println("11100000000000000000000000000000".length());
        System.out.println( ~((1 << 29) - 1));
        System.out.println( Integer.toBinaryString(~((1 << 29) - 1)));
    }

    /**
     * 获取当前电脑可执行的线程数
     */
    @Test
    public void test15(){
        System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void test16() throws InterruptedException {
        final ThreadLocal<String> threadLocal=new InheritableThreadLocal<String>();
        threadLocal.set(UUID.randomUUID().toString());
        System.out.println(Thread.currentThread().getName()+" : "+threadLocal.get());


        ThreadPoolExecutor executorService=new ThreadPoolExecutor(1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>());

        executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            TimeUnit.SECONDS.sleep(5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
            }
        });



        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println(Thread.currentThread().getName()+" : "+threadLocal.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadLocal.remove();
        System.out.println("remove ");
        //System.gc();
        System.out.println("1121212");
        TimeUnit.SECONDS.sleep(10);
    }


}
