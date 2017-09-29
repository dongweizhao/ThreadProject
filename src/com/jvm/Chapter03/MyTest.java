package com.jvm.Chapter03;

import org.junit.Test;

/**
 * Created by dongweizhao on 17/8/7.
 */
public class MyTest {
    private static final int _1MB = 1024 * 1024;

    /**
     * VM 参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8  -XX:+UseSerialGC
     */
    public static void print1() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation3 = new byte[4 * _1MB];
    }
    @Test
    public void test1(){
        print1();
    }

    /**
     * VM 参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
     * -XX:PretenureSizeThreshold=3145728 (byte)
     * 测试大对象直接进入老年代
     */
    public static void print2() {
        byte[] allocation;
        allocation = new byte[4 * _1MB];
    }
    @Test
    public void test2(){
    print2();
    }

    /**
     * VM 参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+UseSerialGC
     *
     * -verbose:gc -Xms30M -Xmx30M -Xmn20M -XX:+PrintGCDetails -XX:SurvivorRatio=2 -XX:MaxTenuringThreshold=1 -XX:+UseSerialGC
     *
     * 长期存活的对象将进入老年代,第二种参数配置可以体现MaxTenuringThreshold 作用
     */
    @Test
    public void testTenuringThreshold(){
        byte []  allocation1,allocation2,allocation3;
        allocation1=new byte[_1MB / 4];//1、放入到eden区256K
        allocation2=new byte[_1MB * 4];//2、存入至Eden区4096,256k则存入到from区,因为有1024K,可以存下256K ,进行minor gc
        allocation3=new byte[_1MB * 4];//3、准备存入Eden区,收集器发现Eden区已有数据4096k,总大小为8192k,没法在存下4096k,需要进行 minor gc,
                                        // 把eden数据区移动值from区,而from已满,并且也存不下4096K,因此分配担保策略,
                                        //移动allocation1、allocation2进入老年代
        allocation3=null;
        allocation3=new byte[_1MB * 4];//进行一次minor Gc,收集年青代中原allocation3数据
    }

    /**
     * VM 参数: -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=1 -XX:+UseSerialGC
     *
     *
     * 长期存活的对象将进入老年代,第二种参数配置可以体现MaxTenuringThreshold 作用
     */
    @Test
    public void testTenuringThreshold2(){
        byte []  allocation1,allocation2,allocation3,allocation4;
        allocation1=new byte[_1MB / 6];
//        allocation2=new byte[_1MB / 4];
        allocation3=new byte[_1MB * 4];
//        allocation4=new byte[_1MB * 4];
//        allocation4=null;
//        allocation4=new byte[_1MB * 4];
    }

    /**
     *
     *  -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure -XX:+
     *  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:-HandlePromotionFailure
     *
     *  -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCApplicationStoppedTime -XX:+PrintGCDateStamps -XX:+PrintReferenceGC  -XX:+PrintGCDetails -Xloggc:gclog.log
     */
    @Test
    public void testHandlePromotionFailure(){
        byte []  allocation1,allocation2,allocation3,allocation4,allocation5,allocation6,allocation7;
        allocation1=new byte[4 *_1MB];
        allocation2=new byte[4 *_1MB];
        allocation3=new byte[2 *_1MB];
        allocation1=null;
        allocation4=new byte[2 *_1MB];//2/3进入至老年代 4进入新生代
        allocation5=new byte[2 *_1MB];//5进入新生代
        allocation6=new byte[2 *_1MB];////6进入新生代
        allocation4=null;
        allocation5=null;
        allocation6=null;
        allocation7=new byte[2* _1MB];
    }


}
