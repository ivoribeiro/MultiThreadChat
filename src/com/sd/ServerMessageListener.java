package com.sd;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

public class ServerMessageListener extends Thread {

    private MessageHandler messageHandler;
    private BufferedReader in;
    private Socket client;


    public ServerMessageListener(MessageHandler messageHandler, Socket client) {
        this.messageHandler = messageHandler;
        this.client = client;
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
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                inputLine = this.client.getInetAddress().getHostAddress() + ":" + this.client.getPort() + " | " + sdf.format(cal.getTime()) + " - " + inputLine;
                this.messageHandler.sendMessage(inputLine);
            }
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }
}
