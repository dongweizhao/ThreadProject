package com.random;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongweizhao on 17/9/15.
 */
public class RoundBinLoadBance {
    static Map<String, Integer> servers = new HashMap<String, Integer>();
    AtomicInteger atomicInteger=new AtomicInteger();

    static {
        servers.put("192.168.1.1", 5);
        servers.put("192.168.1.2", 3);
        servers.put("192.168.1.3", 2);
    }


    public String getNode(int length) {
        int weight=atomicInteger.getAndIncrement()%length;
        int nodeWeight=-1;
        for (Iterator<Map.Entry<String, Integer>> iterator = servers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> entry = iterator.next();
            nodeWeight+=entry.getValue();
            if (weight <= nodeWeight) {
                return entry.getKey();
            }
        }
        return null;
    }

    public int computeWeight() {
        int totalWeight = 0;
        for (Iterator<Map.Entry<String, Integer>> iterator = servers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> entry = iterator.next();
            totalWeight += entry.getValue();
        }
        return totalWeight;
    }


    public static void main(String[] args) {
        RoundBinLoadBance randomLoadBance = new RoundBinLoadBance();
        int totalWeight = randomLoadBance.computeWeight();
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            String ip = randomLoadBance.getNode(totalWeight);
            Integer count = countMap.get(ip);
            if (count != null)
                countMap.put(ip, ++count);
            else {
                countMap.put(ip, 1);
            }
        }
        System.out.println("args = [" + countMap.toString() + "]");

    }
}
