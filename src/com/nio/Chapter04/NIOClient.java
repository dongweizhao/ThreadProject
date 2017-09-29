package com.nio.Chapter04;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by dongweizhao on 17/7/27.
 */
public class NIOClient {
    public Selector selector;

    public NIOClient() throws IOException {
        selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(5000));
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public static void main(String[] args) throws IOException {
        NIOClient nioClient = new NIOClient();
        SocketChannel client;
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        while (true){
            nioClient.selector.select();
            Iterator<SelectionKey> keys = nioClient.selector.selectedKeys().iterator();
            while (keys.hasNext()){
                SelectionKey key=keys.next();
                if (key.isConnectable()){
                    client= (SocketChannel) key.channel();
                    System.out.println("client connect ");
                    if (client.isConnectionPending()){
                        client.finishConnect();
                        System.out.println("conntion finished");
                        buffer.clear();
                        buffer.put("Hello Server".getBytes());
                        buffer.flip();
                        client.write(buffer);
                    }
                    client.register(nioClient.selector,SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    client= (SocketChannel) key.channel();
                    buffer.clear();
                    int count=client.read(buffer);
                    while (count>0){
                        System.out.println("接受到服务端响应:"+new String(buffer.array(),0,count));
                        buffer.clear();
                        count=client.read(buffer);
                    }
                }
            }
        }
    }
}
