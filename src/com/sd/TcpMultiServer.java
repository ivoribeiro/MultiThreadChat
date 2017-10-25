package com.sd;

import java.io.IOException;
import java.net.ServerSocket;

public class TcpMultiServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        int port = 2048;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(port);
            System.err.println("Server: Listning on port: " + port + ".");
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port + ".");
            System.exit(-1);
        }

        while (listening)
            new WorkerThread(serverSocket.accept()).start();

        serverSocket.close();
    }
}
