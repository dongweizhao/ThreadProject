package com.nio.Chapter04;

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
   Processor [] processors=new Processor[Runtime.getRuntime().availableProcessors()];
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

        }
    }

    public static void main(String[] args) throws IOException {
        int port=5000;
        NIOServer nioServer=new NIOServer(port);
        nioServer.listen();
    }

}