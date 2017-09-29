package com.nio.Chapter01;

import java.io.*;

/**对象深拷贝
 * Created by dongweizhao on 17/7/10.
 */
public class Student implements Serializable {
    private static final long serialVersionUID = -8834559347461591191L;

    private Teacher teacher;
    private int age;
    private String name;

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

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
    public Object deepObj() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
        ObjectOutputStream bos=new ObjectOutputStream(outputStream);
        bos.writeObject(this);
        ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(outputStream.toByteArray());
        ObjectInputStream objectInputStream=new ObjectInputStream(byteArrayInputStream);
        return objectInputStream.readObject();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teacher teacher=new Teacher();
        teacher.setAge(10);
        teacher.setName("李老师");
        Student student=new Student();
        student.setName("张三");
        student.setAge(20);
        student.setTeacher(teacher);
        System.out.println("stu = [" + student.getName() +":"+student.getAge()+ "]");
        System.out.println("teach = [" + student.getTeacher().getName() +":"+student.getTeacher().getAge()+ "]");

        Student student1= (Student) student.deepObj();

        System.out.println("stu 2= [" + student1.getName() +":"+student1.getAge()+ "]");
        System.out.println("teach2 = [" + student1.getTeacher().getName() +":"+student1.getTeacher().getAge()+ "]");


        student1.getTeacher().setName("张老师");
        student1.getTeacher().setAge(40);
        System.out.println("stu 2= [" + student1.getName() +":"+student1.getAge()+ "]");
        System.out.println("teach2 = [" + student1.getTeacher().getName() +":"+student1.getTeacher().getAge()+ "]");

    }
}
