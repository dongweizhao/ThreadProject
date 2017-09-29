package com.company.other;

import java.util.Arrays;

/**
 * Created by dongweizhao on 16/8/16.
 */
public class UnboundedStack {
    private int top = -1;
    private Object[] objs;

    public UnboundedStack() throws Exception{
        this(10);
    }

    public UnboundedStack(int capacity) throws Exception{
        if(capacity < 0)
            throw new Exception("Illegal capacity:"+capacity);
        objs = new Object[capacity];
    }

    public void push(Object obj){
        if(top == objs.length - 1){
            this.enlarge();
        }
        objs[++top] = obj;
    }

    public Object pop() throws Exception{
        if(top == -1)
            throw new Exception("Stack is empty!");
        return objs[top--];
    }

    private void enlarge(){
        int num = objs.length/2;
        if(num == 0)
            num = 1;
        objs = Arrays.copyOf(objs, objs.length + num);
    }

    public void dispaly(){
        System.out.print("bottom -> top: | ");
        for(int i = 0 ; i <= top ; i++){
            System.out.print(objs[i]+" | ");
        }
        System.out.print("\n");
    }

    public static void main(String[] args) throws Exception{
        UnboundedStack us = new UnboundedStack(2);
        us.push(1);
        us.push(2);
        us.dispaly();
        System.out.println(us.pop());
        us.dispaly();
        us.push(99);
        us.dispaly();
        us.push(99);
        us.push(99);
        us.push(99);
        us.dispaly();
    }
}
