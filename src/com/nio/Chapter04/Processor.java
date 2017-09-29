package com.nio.Chapter04;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by dongweizhao on 17/7/27.
 */
public class Processor {
    private ExecutorService executorService= Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*2);
    Selector selector;

    public Processor() throws IOException {
        selector=Selector.open();

    }

    private void start() throws IOException {
        while (true){
            selector.select();
            Iterator<SelectionKey> iterator=selector.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey key=iterator.next();
                if (key.isReadable()){
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
            }
        }
    }

}
