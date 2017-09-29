package com.jvm.Chapter02;

import java.util.ArrayList;
import java.util.List;

/**
 * VM args:-XX:PermSize=10M -XX:MaxPermSize=10M
 * 测试运行时常量池溢出测试
 * Created by dongweizhao on 17/7/5.
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
//        List<String> list=new ArrayList<String>();
//        int i=0;
//        while (true){
//            list.add(String.valueOf(i++).intern());
//        }
        String str1=new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern()==str1);
        String str2=new StringBuilder("hee").append("va").toString();
        System.out.println(str2.intern()==str2);
    }
}
