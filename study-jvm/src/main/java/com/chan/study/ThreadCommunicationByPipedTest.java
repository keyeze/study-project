package com.chan.study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Scanner;

public class ThreadCommunicationByPipedTest {
    public static void main(String[] args) throws IOException {
        try (PipedWriter pIn = new PipedWriter();
             PipedReader pOut = new PipedReader();
             BufferedWriter writer = new BufferedWriter(pIn);
             BufferedReader reader = new BufferedReader(pOut)) {
            pOut.connect(pIn);
            Thread inThread = new Thread(() -> {
                String temp = null;
                while (true) {
                    try {
                        if (reader.ready()) {
                            System.out.println(Thread.currentThread());
                            while (reader.ready()) {
                                System.out.println(reader.readLine());
                            }
                        }
                        Thread.sleep(10000L);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "print out main's word");
            inThread.start();
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String str = scanner.next();
                writer.write(str);
                writer.newLine();
                writer.flush();
//                System.out.println(Thread.currentThread() + ":" + str);
            }
        }
    }
}
