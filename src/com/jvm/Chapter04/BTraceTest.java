package com.jvm.Chapter04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by dongweizhao on 17/8/23.
 */
public class BTraceTest {
    public int add(int a,int b){
        return a+b;
    }

    public static void main(String[] args) throws IOException {
        BTraceTest bTraceTest=new BTraceTest();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
        for (int i=0;i<10;i++){
            bufferedReader.readLine();
            int a= (int) Math.round(Math.random()*1000);
            int b= (int) Math.round(Math.random()*1000);
            System.out.println(bTraceTest.add(a,b));
        }
    }
}
