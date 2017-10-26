package com.sd;

import com.sun.security.ntlm.Client;

import java.io.*;
import java.net.*;

public class ChatClient extends Thread {

    public static void main(String[] args) throws IOException {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }

    public void run() {
        Socket chatSocket = null;
        PrintWriter out = null;
        InputStream in = null;
        String HOST = "localhost";
        try {
            chatSocket = new Socket(HOST, 2048);
            System.out.println("Ligado ao servidor " + chatSocket.getLocalSocketAddress());
            // establece buffer para o que e enviado para o servidor
            out = new PrintWriter(chatSocket.getOutputStream(), true);
            // establece buffer para o que  vindo do servidor
            in = chatSocket.getInputStream();
            Screen screen = new Screen("Hi multithread chat");
            screen.start();
            ClientMessageListener clientMessageListner = new ClientMessageListener(screen);
            clientMessageListner.addInputStream(in);
            clientMessageListner.start();

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + HOST);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                    + "the connection to: " + HOST);
            System.exit(1);
        }
        //Buffer do teclado
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        // while para ler do teclado
        try {
            System.out.println("User Input:");
            while ((userInput = stdIn.readLine()) != null) {
                // envia para o servidor
                out.println(userInput);
            }
            System.out.println("OUT CLOSE");
            out.close();
            System.out.println("STDIN CLOSE");
            stdIn.close();
            System.out.println("chatSocket CLOSE");
            chatSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}