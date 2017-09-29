package com.company.test;

/**
 * Created by dongweizhao on 16/7/19.
 */
public class InsideClassTest005 {
    private int age = 10;
    private static String sex = "男";

    public static void main(String[] args) {
        sudent sudent=new sudent();
        student2 student2=new InsideClassTest005().createStudent2();
    }

    static class sudent {
        private static String name = "张三";
        void sai() {
            System.out.println(sex);
        }
        //在静态内部类中，可以定义静态的方法(也只有在静态的内部类中可以定义静态的方法)，在静态方法中引用外部类的成员。
        static  void sai1(){
            System.out.println(sex);
        }
    }

    class student2 {
        final private static String name = "李四";
        void sai(){
            System.out.println(age);
            System.out.println(sex);
        }
    }
    abstract static  class StudentAbs3{
        static String name="王五";
        public  void say(){

        }
    }

    static class  Student3 extends StudentAbs3{
        @Override
        public void say() {
            super.say();
            System.out.printf(name);
        }
    }

    student2 createStudent2(){
        return new student2();
    }


}
