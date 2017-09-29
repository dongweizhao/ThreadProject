package com.company.Chapter06;

import com.company.Foo;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dongweizhao on 17/6/4.
 */
public class CheckFood implements Runnable {
    private DelayQueue<Food> delayQueue;

    public CheckFood(DelayQueue<Food> delayQueue) {
        this.delayQueue = delayQueue;
    }

    @Override
    public void run() {
        try {
            System.out.println("开始检查");
            boolean flag = true;
            while (flag) {

                Food food = delayQueue.take();
                System.out.println(food.getName() + "食物过期,保存时间:" + food.getSaveTime() + "天");
                if (delayQueue.isEmpty()) {
                    flag = false;
                }
            }
            System.out.println("检查完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DelayQueue<Food> foodDelayQueue = new DelayQueue<Food>();

        foodDelayQueue.add(new Food("西红柿", 20));
        foodDelayQueue.add(new Food("西瓜", 30));
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new CheckFood(foodDelayQueue));
        executorService.shutdown();
    }
}
