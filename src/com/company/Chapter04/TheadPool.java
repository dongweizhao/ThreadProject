package com.company.Chapter04;

/**
 * 线程池接口
 * Created by dongweizhao on 16/11/20.
 */
public interface TheadPool<Job extends Runnable> {

    /**
     * 执行任务
     *
     * @param job
     */
    public void execute(Job job);

    /**
     * 添加线程数
     *
     * @param num
     * @return
     */
    public int addWorker(int num);

    /**
     * 减少线程数
     *
     * @param num
     * @return
     */
    public int removeWorker(int num);

    /**
     * 获取等待执行任务
     *
     * @return
     */
    public int getWaitSize();


    /**
     * 停止线程池
     */
    public void shutdown();

}
