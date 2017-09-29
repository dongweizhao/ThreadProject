package com.company.generate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dongweizhao on 17/6/24.
 */
public class SingleGenerate {
    public static void main(String[] args) throws IOException {
        System.out.println("开始时间 = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
         FileOutputStream[] fop=new FileOutputStream[6];
        int node=0;
        for (int i=1;i<9000_0000;i++){
            node=i%6;
            if (fop[node]==null){
                fop[node]=new FileOutputStream(new File("/Users/dongweizhao/Desktop/test/file_"+node+".txt"));
            }
            StringBuffer stringBuffer = new StringBuffer(String.valueOf(i));
            stringBuffer.append("\n");
            fop[node].write(stringBuffer.toString().getBytes());
        }
        for (int j=0;j<6;j++){
                try {
                    fop[j].flush();
                    fop[j].close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        System.out.println("结束时间 = [" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "]");
    }
}
