package com.sd.multicast.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ServerMessageListener extends Thread {

    private ServerMessageHandler messageHandler;
    private BufferedReader in;
    private Socket client;


    public ServerMessageListener(ServerMessageHandler messageHandler, Socket client, InputStream inputStream) {
        this.messageHandler = messageHandler;
        this.client = client;
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
