package com.nio.Chapter03;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dongweizhao on 17/7/27.
 */
public class Processor {
    private ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);

    public void process(final SelectionKey key){
        executorService.submit(new Runnable() {
            @Override
            public void run() {

                ByteBuffer buffer=ByteBuffer.allocate(1024);
                SocketChannel channel= (SocketChannel) key.channel();
                try {
                    int count=channel.read(buffer);
                    while (count>0){
                        System.out.println("服务器端接受客户端数据--:"+new String( buffer.array(),0,count));
                        buffer.clear();
                        count=channel.read(buffer);
                    }
                   // channel.close();
                    //key.cancel();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
