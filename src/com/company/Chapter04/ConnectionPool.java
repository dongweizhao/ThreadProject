package com.company.Chapter04;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * 数据连接池
 * Created by dongweizhao on 16/11/16.
 */
public class ConnectionPool {
    LinkedList<Connection> pool = new LinkedList<Connection>();

    public ConnectionPool(int size) {
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                pool.addLast(ConnectionDriver.createConnection());
            }
        }
    }

    public void relaseConnection(Connection connection) {
        if (connection != null) {
            synchronized (pool) {
                System.out.println("relaseConnection Thread name = [" + Thread.currentThread().getName() + "]");
                pool.addLast(connection);
                pool.notifyAll();
            }
        }
    }

    public Connection fetchConnection(long mills) throws InterruptedException {
        synchronized (pool) {
            System.out.println("fetchConnection Thread name = [" + Thread.currentThread().getName() + "]");
            if (mills <= 0) {
                while (pool.isEmpty()) {
                    pool.wait();
                }
                return pool.removeFirst();
            } else {
                long now = System.currentTimeMillis() + mills;
                long fre = mills;

                while (pool.isEmpty() && fre > 0) {
                    System.out.println("fetchConnection Thread name  = [" + Thread.currentThread().getName() + "] 进去 wating,等待时间:"+fre);

                    pool.wait(fre);
                    fre = now - System.currentTimeMillis();
                    if (fre<=0){
                        System.out.println("fetchConnection Thread name  = [" + Thread.currentThread().getName() + "] 超时 wating,超时时间:"+fre);
                    }else{
                        System.out.println("fetchConnection Thread name  = [" + Thread.currentThread().getName() + "] 被唤醒 wating");
                    }

                }
                Connection result = null;
                if (!pool.isEmpty()) {
                    result = pool.removeFirst();
                }
                return result;
            }
        }
    }
}
