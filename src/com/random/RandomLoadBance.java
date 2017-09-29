package com.random;

import java.util.*;

/**
 * Created by dongweizhao on 17/9/15.
 */
public class RandomLoadBance {
    static Map<String, Integer> servers = new HashMap<String, Integer>();
    Random random = new Random();

    static {
        servers.put("192.168.1.1", 5);
        servers.put("192.168.1.2", 3);
        servers.put("192.168.1.3", 2);
    }

    public int getNode(int length) {
        return random.nextInt(length);
    }

    public List computeWeight() {
        List<String> nodes = new ArrayList();
        for (Iterator<Map.Entry<String, Integer>> iterator = servers.entrySet().iterator(); iterator.hasNext(); ) {
            Map.Entry<String, Integer> entry = iterator.next();
            for (int i = 0; i < entry.getValue(); i++) {
                nodes.add(entry.getKey());
            }
        }
        return nodes;
    }


    public static void main(String[] args) {
        RandomLoadBance randomLoadBance = new RandomLoadBance();
        List<String> list = randomLoadBance.computeWeight();
        Map<String, Integer> countMap = new HashMap<>();
        for (int i = 0; i < 1000; i++) {
            int n = randomLoadBance.getNode(list.size());
            String ip = list.get(n);
            Integer count = countMap.get(ip);
            if (count != null)
                countMap.put(ip, ++count);
            else {
                countMap.put(ip, 0);
            }
        }
        System.out.println("args = [" + countMap.toString() + "]");

    }
}
