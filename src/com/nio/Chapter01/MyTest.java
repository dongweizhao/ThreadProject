package com.nio.Chapter01;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * Created by dongweizhao on 17/7/25.
 */
public class MyTest {
    /**
     * 读写数据到缓存区
     */
    @Test
    public void test1() throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("/Users/dongweizhao/Documents/platform/ThreadProject/src/nio-data.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(40);
        int bytesRead = fileChannel.read(buffer);
        while (bytesRead != 1) {
            buffer.flip();
            while (buffer.hasRemaining()) {
                System.out.println((char) buffer.get());
            }
            buffer.clear();
            bytesRead = fileChannel.read(buffer);
        }
        randomAccessFile.close();
    }

    /**
     * 字符串转byte,bytebuff转字符串
     * @throws CharacterCodingException
     */
    @Test
    public void test2() throws CharacterCodingException {
        String str = "a";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        Charset charset = null;
        CharsetDecoder decoder = null;
        CharBuffer charBuffer = null;
        charset = Charset.forName("UTF-8");
        decoder = charset.newDecoder();
        charBuffer = decoder.decode(buffer.asReadOnlyBuffer());
        System.out.println(charBuffer.toString());
    }
}
