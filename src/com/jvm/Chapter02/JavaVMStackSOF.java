package com.jvm.Chapter02;

/**
 * VM args: -Xss 128K
 * 虚拟机栈测试,测试栈深度
 * Created by dongweizhao on 17/7/4.
 */
public class JavaVMStackSOF {
    private int statckLength=1;
    public void statckLeak(){
        try {
            statckLength++;
            statckLeak();
        } catch (Exception e) {
            throw  e;
        }
    }

    public static void main(String[] args) {
        JavaVMStackSOF javaVMStackSOF=new JavaVMStackSOF();
        try {
            javaVMStackSOF.statckLeak();
        } catch (Exception e) {
            System.out.println("stack Length = [" + javaVMStackSOF.statckLength + "]");
            throw  e;
        }
    }
}
