package com.random;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dongweizhao on 17/9/15.
 */
public class RoundBinLoadBance2 {
    static LinkedHashMap<String, IntegerWrapper> servers = new LinkedHashMap<String, IntegerWrapper>();
    static int maxWeight = 5;
    static int minWeight = 2;
    AtomicInteger atomicInteger = new AtomicInteger();
    static List<String> serverList = new ArrayList<String>();

    static {
        servers.put("192.168.1.1", new IntegerWrapper(5));
        servers.put("192.168.1.2", new IntegerWrapper(3));
        servers.put("192.168.1.3", new IntegerWrapper(2));
        serverList.addAll(servers.keySet());
    }

    private static final class IntegerWrapper {
        public IntegerWrapper(int value) {
            this.value = value;
        }

        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public void decrement() {
            this.value--;
        }
    }


    public String getNode(int totalWeight) {
        int current = atomicInteger.getAndIncrement();
        if (maxWeight > 0 && minWeight < maxWeight) { // 权重不一样
            int mod = current % totalWeight;
            for (int i = 0; i < maxWeight; i++) {
                for (Map.Entry<String, IntegerWrapper> each : servers.entrySet()) {
                    final IntegerWrapper v = each.getValue();
                    if (mod == 0 && v.getValue() > 0) {
                        return each.getKey();
                    }
                    if (v.getValue() > 0) {
                        v.decrement();
                        mod--;
                    }
                }
            }
        }
        return serverList.get(current % servers.size());
    }

    public int computeWeight() {
        int totalWeight = 0;
        for (Iterator<Map.Entry<String, IntegerWrapper>> iterator = servers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, IntegerWrapper> entry = iterator.next();
            totalWeight += entry.getValue().getValue();
        }
        return totalWeight;
    }


    public static void main(String[] args) {
        RoundBinLoadBance2 randomLoadBance = new RoundBinLoadBance2();
        int totalWeight = randomLoadBance.computeWeight();
        Map<String, Integer> countMap = new HashMap<String, Integer>();
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
