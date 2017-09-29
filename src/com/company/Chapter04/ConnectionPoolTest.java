package com.company.Chapter04;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongweizhao on 16/11/16.
 */
public class ConnectionPoolTest {
    static ConnectionPool pool = new ConnectionPool(2);
    static CountDownLatch start = new CountDownLatch(1);
    static CountDownLatch end = null;

    public static void main(String[] args) throws InterruptedException {
        int count = 20, threadCount = 4;
        end = new CountDownLatch(threadCount);
        AtomicInteger go = new AtomicInteger();
        AtomicInteger gonot = new AtomicInteger();


        for (int i = 0; i < threadCount; i++) {
            Thread t = new Thread(new ConnectionRunner(go, gonot, count),"Thread--"+i);
            t.start();
        }
        start.countDown();
        end.await();
        System.out.println("total execue " + (threadCount * count));
        System.out.println("go = [" + go + "]");
        System.out.println("nogot = [" + gonot + "]");
    }

    static class ConnectionRunner implements Runnable {
        int cout;
        AtomicInteger go;
        AtomicInteger gonot;

        public ConnectionRunner(AtomicInteger go, AtomicInteger nogot, int count) {
            this.go = go;
            this.gonot = nogot;
            this.cout = count;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (cout > 0) {
                try {

                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            pool.relaseConnection(connection);
                            go.incrementAndGet();
                        }

                    } else {
                        gonot.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cout--;
                }
            }
            end.countDown();
        }
    }
}
