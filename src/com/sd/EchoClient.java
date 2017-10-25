package com.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {
    public static void main(String[] args) throws IOException {

        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        String HOST = "localhost";

        try {
            echoSocket = new Socket(HOST, 1028);
            System.out.println("Ligado ao servidor " + echoSocket.getLocalSocketAddress());
            // establece buffer para o que e enviado para o servidor
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            // establece buffer para o que  vindo do servidor
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));

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
        while ((userInput = stdIn.readLine()) != null) {
            System.out.println("User Imput");
            // envia para o servidor
            out.println(userInput);
            // escreve o que recebe recebe do servidor
            System.out.println("echo: " + in.readLine());
        }
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}