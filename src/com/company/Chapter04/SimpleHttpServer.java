package com.company.Chapter04;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**简易版http服务器
 * Created by dongweizhao on 16/11/21.
 */
public class SimpleHttpServer {
    private static int port = 8080;
    private static String basePath;
    static ServerSocket serverSocket;
    static TheadPool<HttpRequestHandler> thpool = new DefaultThreadPool<HttpRequestHandler>(1);

    public static void main(String[] args) throws IOException {
        SimpleHttpServer.setPort(8080);
        SimpleHttpServer.setBasePath("/Users/dongweizhao/Documents/platform/ThreadProject/src/com/company/Chapter04");
        SimpleHttpServer.start();

    }
    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String path) {
        if (path != null && new File(path).exists() && new File(path).isDirectory()) {
            SimpleHttpServer.basePath = path;
        }
    }

    public static void start() throws IOException {
        serverSocket = new ServerSocket(port);
        Socket socker = null;
        while ((socker = serverSocket.accept()) != null) {
            thpool.execute(new HttpRequestHandler(socker));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {

        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;
            BufferedReader reader = null;
            BufferedReader br = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                if (filePath.endsWith("jpg") || filePath.endsWith("ico")||filePath.endsWith("png")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server:Molly");
                    out.println("Content-Type:image/jpeg");
                    out.println("Content-Length:" + array.length);
                    out.println("");
                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server:Molly");
                    out.println("Content-Type:text/html;charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                }
                out.flush();
            } catch (IOException e) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {
                close(br, in, reader, out, socket);
            }

        }

        private static void close(Closeable... closeables) {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    try {
                        if (closeable!=null){
                            closeable.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }
}
