package com.nio.Chapter04;

/**
 * Created by dongweizhao on 17/7/28.
 */
public class MyTest {

    public static void main(String[] args) {
        P [] ps=new P[2];
        for (int i=0;i<2;i++){
            ps[i]=new P();
        }
        for (int i = 0; i <10 ; i++) {
             P p=ps[i%2];
        }
    }
}
class  P{

    public P() {
        this.start();
    }

    public void start(){
        System.out.println("Hello 1");
    }
}