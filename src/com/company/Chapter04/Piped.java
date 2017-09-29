package com.company.Chapter04;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * Created by dongweizhao on 16/11/15.
 */
public class Piped {

    public static void main(String[] args) throws IOException {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int reseive = 0;
        try {
            while ((reseive = System.in.read()) != -1) {
                out.write(reseive);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    static class Print implements Runnable {

        private PipedReader in;

        public Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int reseive = 0;
            try {
                while ((reseive = in.read()) != -1) {
                    System.out.print((char) reseive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
