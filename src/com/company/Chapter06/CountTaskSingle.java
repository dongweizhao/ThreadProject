package com.company.Chapter06;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 单线程读取文件数
 * Created by dongweizhao on 17/6/19.
 */
public class CountTaskSingle{
        static int sum = 0;

        public void countDir(File file)
        {
            if(file.isDirectory())
            {
                for(File f:file.listFiles())
                    countDir(f);
            }
            else
                sum++;

        }

        public static void main(String[] args) {
            CountTaskSingle ins = new CountTaskSingle();
            System.out.println("系统日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) ;
            ins.countDir(new File("/Users/dongweizhao/Documents/platform"));
            System.out.println("文件数:"+sum);
            System.out.println("系统日期："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date())) ;

            /**
             *
             *

                系统日期：2017-06-19 14:55:06.242
                文件数:843969
                系统日期：2017-06-19 14:55:53.226
                47

                 系统日期：2017-06-19 14:57:58.521
                 文件数:1047303
                 系统日期：2017-06-19 14:59:10.638
                 72

             *
             *
             */

        }
}
