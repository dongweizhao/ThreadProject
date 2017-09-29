package com.nio.Chapter01;

import java.io.Serializable;

/**
 * Created by dongweizhao on 17/7/10.
 */
public class Teacher implements Serializable {
    private static final long serialVersionUID = -8834559347461591191L;
    private int age;
    private String name;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
