package com.company.algorithm;

import org.junit.Test;

import java.util.HashMap;

/**
 * Created by dongweizhao on 16/11/5.
 */
public class TestSort {
    //冒泡
    @Test
    public void BubbleSorttest(){
     int a[]={1,54,6,3,78,34,12,45};
        for (int i = 0; i <a.length ; i++) {
            for (int j=i+1;j<a.length;j++){
                int temp=a[i];
                if(a[j]<temp){
                    a[i]=a[j];
                    a[j]=temp;
                }
            }
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println("a["+i+"]:"+a[i]);
        }
    }

    //选择排序
    @Test
    public void selectSorttest(){
        int a[]={1,54,6};
        for (int i = 0; i <a.length ; i++) {
            int position=i;
            int temp=a[i];
            for (int j=i+1;j<a.length;j++){
                if(a[j]<temp){
                    temp=a[j];
                    position=j;
                }
            }
            a[position]=a[i];
            a[i]=temp;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println("a["+i+"]:"+a[i]);
        }
    }
    public void test2(){
        HashMap hashMap=new HashMap();
    }
}
