package com.company.Chapter04;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * Created by dongweizhao on 16/11/12.
 */
public class MultiThead {
    public static void main(String[] args) {
        //获取java线程管理MXBean
        ThreadMXBean threadMXBean= ManagementFactory.getThreadMXBean();
        //不需要获取同步的monitor和synchronizer信息,仅获取线程和线程堆栈信息
        ThreadInfo [] threadInfos=threadMXBean.dumpAllThreads(false,false);
        for (ThreadInfo threadInfo:threadInfos) {
            System.out.println("[" + threadInfo.getThreadId() + "] "+threadInfo.getThreadName());
        }
    }
}
