package com.sd;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer extends Thread {
    private int port;
    private MessageHandler messageHandler;

    /**
     * Server constructor
     *
     * @param port port that the server is listing
     */
    public ChatServer(int port) {
        this.port = port;
    }

    public void run() {
        boolean listing = true;
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);
            System.out.println("Server: Listning on port: " + this.port + ".");
            this.messageHandler = new MessageHandler();
            while (listing) {
                // cria o socket do cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connection: " + clientSocket.getInetAddress().getHostAddress() + " port:" + clientSocket.getPort());

                // adiciona o out do cliente como out do message handler
                try {
                    OutputStream out = clientSocket.getOutputStream();
                    messageHandler.addOutputStream(out);
                    System.out.println("Output added to message handler");
                    ServerMessageListener messageListner = new ServerMessageListener(this.messageHandler, clientSocket);
                    messageListner.addInputStream(clientSocket.getInputStream());
                    System.out.println("Input added to message listner");
                    messageListner.start();
                } catch (IOException e) {
                    System.err.println("Could not get client stream.");
                }
            }
            //messageHandler.start();
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + this.port + ".");
            System.exit(-1);
        }
    }

    public static void main(String[] args) throws IOException {
        // starts a server on port 2048
        ChatServer chatServer = new ChatServer(2048);
        chatServer.start();
    }


}
