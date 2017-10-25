package com.sd;

import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String[] args) throws IOException {

        Socket chatSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String HOST = "localhost";

        try {
            chatSocket = new Socket(HOST, 2048);
            System.out.println("Ligado ao servidor " + chatSocket.getLocalSocketAddress());
            // establece buffer para o que e enviado para o servidor
            out = new PrintWriter(chatSocket.getOutputStream(), true);
            // establece buffer para o que  vindo do servidor
            in = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
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
        System.out.println("User Input:");
        // while para ler do teclado
        while ((userInput = stdIn.readLine()) != null) {
            // envia para o servidor
            out.println(userInput);
            // escreve o que recebe recebe do servidor
            System.out.println("echo: " + in.readLine());
        }
        out.close();
        in.close();
        stdIn.close();
        chatSocket.close();
    }
}