package com.jvm.Chapter02;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试堆溢出
 * VM Args:-Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * Created by dongweizhao on 17/7/4.
 */
public class HeapOOM {
    //static class OOMObject{}

    public static void main(String[] args) {
        List<OOMObject> list=new ArrayList<OOMObject>();
        for (;;){
            list.add(new OOMObject());
        }
    }
}
class OOMObject{}
