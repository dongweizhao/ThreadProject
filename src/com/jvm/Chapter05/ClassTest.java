package com.jvm.Chapter05;

import java.io.FileInputStream;

/**
 * Created by dongweizhao on 17/9/5.
 */
public class ClassTest {
    public static void main(String[] args) {
        try{
            //读取文件数据,文件是当前目录下的First.class
            FileInputStream fis = new FileInputStream("/Users/dongweizhao/Documents/platform/ThreadProject/src/Test.class");

            int length = fis.available();
            //文件数据
            byte[] data = new byte[length];

            //读取文件到字节数组
            fis.read(data);

            //关闭文件
            fis.close();

            //解析文件数据
            parseFile(data);

        }catch(Exception e){
            System.out.println(e);
        }
    }
    private static void parseFile(byte[] data){
        //输出魔数
        System.out.print("魔数(magic):0x");
        System.out.println("data = [" + Integer.toHexString(data[0]) + "]");
        System.out.print(Integer.toHexString(data[0]).substring(6).toUpperCase());
        System.out.print(Integer.toHexString(data[1]).substring(6).toUpperCase());
        System.out.print(Integer.toHexString(data[2]).substring(6).toUpperCase());
        System.out.println(Integer.toHexString(data[3]).substring(6).toUpperCase());

        //主版本号和次版本号码
        int minor_version = (((int)data[4]) << 8) + data[5];
        int major_version = (((int)data[6]) << 8) + data[7];

        System.out.println("版本号(version):" + major_version + "." + minor_version);
    }
}
