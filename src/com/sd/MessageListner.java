package com.sd;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MessageListner extends Thread {

    private MessageHandler messageHandler;
    private BufferedReader in;


    public MessageListner(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Add an input stream to message listner
     *
     * @param inputStream
     */
    public void addInputStream(InputStream inputStream) {
        this.in = new BufferedReader(new InputStreamReader(inputStream));
    }

    /**
     * Metodo de execução da thread, escuta o input stream dado na instancia
     * e envia o seu output para o message handler
     */
    public void run() {
        String inputLine;
        try {
            while ((inputLine = this.in.readLine()) != null) {
                System.out.println("Message Listner: Mensagem recebida");
                System.out.println(inputLine);
                this.messageHandler.sendMessage(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }
}
