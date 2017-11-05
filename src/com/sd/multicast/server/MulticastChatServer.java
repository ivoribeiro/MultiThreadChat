package com.sd.multicast.server;

import java.io.IOException;
import java.net.*;


public class MulticastChatServer extends Thread {
    private DatagramSocket socket = null;
    private ServerSocket serverSocket = null;

    public static void main(String[] args) throws java.io.IOException {
        new MulticastChatServer().start();
    }

    public MulticastChatServer() throws IOException {
        super("MulticastServerThread");
        this.serverSocket = new ServerSocket(2048);
    }

    public void run() {
        boolean active = true;

        try {
            System.out.println("Server: Listning on port: " + this.serverSocket.getLocalPort() + ".");
            socket = new DatagramSocket(4445);
            InetAddress group = InetAddress.getByName("230.0.0.1");
            ServerMessageHandler mh = new ServerMessageHandler(socket, group);
            while (active) {
                // cria o socket do cliente
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connection: " + clientSocket.getInetAddress().getHostAddress() + " port:" + clientSocket.getPort());
                ServerMessageListener serverMessageListener = new ServerMessageListener(mh, clientSocket, clientSocket.getInputStream());
                serverMessageListener.start();

            }
            socket.close();

        } catch (IOException e) {
            System.err.println("Could not listen");
            System.exit(-1);
        }
    }
}
