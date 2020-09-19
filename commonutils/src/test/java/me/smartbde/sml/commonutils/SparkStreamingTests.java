package me.smartbde.sml.commonutils;

import org.junit.Test;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SparkStreamingTests {
    @Test
    public void test() throws Exception {
        ServerSocket serverSocket = null;
        PrintWriter pw = null;
        try {
            serverSocket = new ServerSocket(9999);
            System.out.println("服务启动，等待连接");
            Socket socket = serverSocket.accept();
            System.out.println("连接成功，来自：" + socket.getRemoteSocketAddress());
            pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
            int j = 0;
            while (j < 100) {
                j++;
                String str = "spark streaming test " + j;
                pw.println(str);
                pw.flush();
                System.out.println(str);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                pw.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
