package com.company.Chapter04;

import java.util.concurrent.TimeUnit;

/**
 * Created by dongweizhao on 16/11/12.
 */
public class SleepUtils {
    public static void second(long s) throws InterruptedException {
        TimeUnit.SECONDS.sleep(s);
    }
}
