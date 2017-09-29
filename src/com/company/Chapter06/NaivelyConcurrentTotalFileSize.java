package com.company.Chapter06;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * 基于newFixedThreadPool,如果目录下所属的文件夹大于线程数，会导致线程之间相互等待，造成死锁。
 * Created by dongweizhao on 17/6/19.
 */
public class NaivelyConcurrentTotalFileSize {
    private long getTotalSizeOfFilesInDir(final ExecutorService service,
                                          final File file) throws InterruptedException, ExecutionException,
            TimeoutException {
        if (file.isFile())
            return file.length();

        long total = 0;
        final File[] children = file.listFiles();

        if (children != null) {
            final List<Future<Long>> partialTotalFutures = new ArrayList<Future<Long>>();
            for (final File child : children) {
                partialTotalFutures.add(service.submit(new Callable<Long>() {
                    public Long call() throws InterruptedException,
                            ExecutionException, TimeoutException {
                        return getTotalSizeOfFilesInDir(service, child);
                    }
                }));
            }

            for (final Future<Long> partialTotalFuture : partialTotalFutures)
                total += partialTotalFuture.get();

        }

        return total;

    }

    private long getTotalSizeOfFile(final String fileName)
            throws InterruptedException, ExecutionException, TimeoutException {
        final ExecutorService service = Executors.newFixedThreadPool(100);
        try {
            return getTotalSizeOfFilesInDir(service, new File(fileName));
        } finally {
            service.shutdown();
        }
    }

    public static void main(final String[] args) throws InterruptedException,
            ExecutionException, TimeoutException {
        System.out.println("系统日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
        final long total = new NaivelyConcurrentTotalFileSize()
                .getTotalSizeOfFile("/Users/dongweizhao/Documents/platform");
        System.out.println("文件数:"+total);
        System.out.println("系统日期：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    }
}
