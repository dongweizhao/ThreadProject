package com.company;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Thread thread=new Thread(){
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("name:" + Thread.currentThread().getName());
                }

            }

        };
        thread.start();

        Thread thread1=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Runnable:" + Thread.currentThread().getName());
                }

            }
        });
        thread1.start();
    }
}
