package com.nio.Chapter03;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 多线程Reactor模式
 * 多线程监控所有请求,并行处理多个读写操作
 *
 */
public class NIOServer {

    Selector select=null;

    public NIOServer(int  port) throws IOException {
        select= Selector.open();
        ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(port));
        serverSocketChannel.register(select,SelectionKey.OP_ACCEPT);
        System.out.println("Server Start----"+port);
    }
    public void listen() throws IOException {
        while (true){
            select.select();
            Iterator<SelectionKey> iterator=select.selectedKeys().iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey=iterator.next();
                handler(selectionKey);
                iterator.remove();
            }
        }
    }
    private void handler(SelectionKey key) throws IOException {
        if (key.isValid()&&key.isAcceptable()){
            ServerSocketChannel channel= (ServerSocketChannel) key.channel();
            SocketChannel socketChannel=channel.accept();
            socketChannel.configureBlocking(false);
            System.out.println("Accept request form "+socketChannel.getRemoteAddress());
            SelectionKey key1=socketChannel.register(select,SelectionKey.OP_READ);
            if (key==key1){
                System.out.println("key == key1");
            }
            key1.attach(new Processor());
        }else if (key.isValid()&&key.isReadable()){
            Processor processor= (Processor) key.attachment();
            processor.process(key);
        }
    }

    public static void main(String[] args) throws IOException {
        int port=5000;
        NIOServer nioServer=new NIOServer(port);
        nioServer.listen();
    }

}