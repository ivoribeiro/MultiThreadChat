package com.sd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Client message listner, it handle the messages
 * coming from socker input stream and send them
 * to the screen
 */
public class ClientMessageListener extends Thread {

    private BufferedReader in;
    private Screen messageHandler;


    public ClientMessageListener(Screen messageHandler) {
        this.messageHandler = messageHandler;
    }

    /**
     * Add an input stream to message listener instance
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
                System.out.println("Client message listner: Mensagem recebida");
                System.out.println(inputLine);
                this.messageHandler.processMessage(inputLine);
            }
            //close the buffer reader
            this.in.close();
        } catch (IOException e) {
            System.out.println("Error reading buffer");
            e.printStackTrace();
        }
    }
}
