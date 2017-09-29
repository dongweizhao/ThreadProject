package com.jvm.Chapter02;

/** VM args:-Xss2M
 * 测试批量创建线程内存溢出
 * Created by dongweizhao on 17/7/5.
 */
public class JavaVMStackOOM {
    private void dontStop(){
        while (true){}
    }
    public void stackLeakByThead(){
        while (true){
            Thread thread=new Thread(new Runnable() {
                private String name;
                private int age;
                private String sex;
                private void say(){
                    System.out.println("12121212");
                }
                @Override
                public void run() {
                    dontStop();
                }
            });
            thread.start();
        }

    }

    public static void main(String[] args) {
//        JavaVMStackOOM javaVMStackOOM=new JavaVMStackOOM();
//        javaVMStackOOM.stackLeakByThead();
    }
}
