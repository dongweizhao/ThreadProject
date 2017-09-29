package com.company.Chapter06;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 食物类
 * Created by dongweizhao on 17/6/4.
 */
public class Food implements Delayed {
    private String name;
    private long saveTime;//保存时间
    private long expireTime;//过期时间:保存时间+当前时间

    public Food(String name, long saveTime) {
        this.name = name;
        this.saveTime = saveTime;
        this.expireTime = TimeUnit.NANOSECONDS.convert(saveTime,TimeUnit.SECONDS)+System.nanoTime();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expireTime-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Food food= (Food) o;
        if (this.expireTime>food.getExpireTime()){//还未过期
            return 1;
        }else  if (this.expireTime==food.getExpireTime()){
            return 0;
        }else {
            return -1;
        }
    }
}
