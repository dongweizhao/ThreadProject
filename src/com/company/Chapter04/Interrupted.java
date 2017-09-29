package com.company.Chapter04;

/**
 * 线程中断状态控制
 * Created by dongweizhao on 16/11/14.
 */
public class Interrupted {

    public static void main(String[] args) throws InterruptedException {
            Thread3 t = new Thread3();
            t.start();
            System.out.println("thread3 isInterrupted[" + t.isInterrupted() + "]");


//        Thread.sleep(1000);
//        t.interrupt();
//        System.out.println("thread3 isInterrupted[" + t.isInterrupted() + "]");
//        System.out.println("thread3 isInterrupted[" + t.isInterrupted() + "]");
//        System.out.println("thread3 isInterrupted[" + t.isInterrupted() + "]");
//        Thread4 p = new Thread4(Thread.currentThread());
//        p.start();
//        try {
//            p.join();
//        } catch (InterruptedException e) {
//            System.out.println("Parent thread will die...");
//        }
    }
   static class Thread3 extends Thread{
        @Override
        public void run() {
            while(true){
//                try {
//                    TimeUnit.SECONDS.sleep(4);
//                } catch (InterruptedException e) {
//                    System.out.println("进入异常了");
//                    e.printStackTrace();
//                    return;
//                }
                if(currentThread().interrupted()){
                    System.out.println("Someone interrupted me.");
                }
                else{
                    System.out.println("Going...");
                }
                long now = System.currentTimeMillis();
                while(System.currentTimeMillis()-now<1000){
                    // 为了避免Thread.sleep()而需要捕获InterruptedException而带来的理解上的困惑,
                    // 此处用这种方法空转1秒
                }
            }
        }
    }
    static class Thread4 extends Thread{
        private Thread parent;
        public Thread4(Thread parent){
            this.parent = parent;
        }

        public void run() {
            while (true) {
                System.out.println("sub thread is running...");
                long now = System.currentTimeMillis();
                while (System.currentTimeMillis() - now < 2000) {
                    // 为了避免Thread.sleep()而需要捕获InterruptedException而带来的理解上的困惑,
                    // 此处用这种方法空转2秒
                }
                parent.interrupt();
            }
        }
    }
}
