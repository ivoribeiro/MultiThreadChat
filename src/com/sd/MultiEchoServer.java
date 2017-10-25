package com.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiEchoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        Integer PORT_NUM = 2048;
        boolean listening = true;
        //envia
        PrintWriter out = null;
        //recebe
        BufferedReader in = null;
        try {
            serverSocket = new ServerSocket(PORT_NUM);
            System.out.println("Listing on port: " + PORT_NUM);
        } catch (IOException e) {
            System.out.println("Porta nao disponivel");
            System.exit(-1);
        }
        while (listening) {
            new WorkerThread(serverSocket.accept()).start();
        }
        serverSocket.close();
    }
}
