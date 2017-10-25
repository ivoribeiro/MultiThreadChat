package com.sd;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class MessageHandler extends Thread {

    //private LinkedList<String> messageQueue;
    private ArrayList<BufferedWriter> outs;


    public MessageHandler() {
        //this.messageQueue = new LinkedList<>();
        this.outs = new ArrayList<BufferedWriter>();
    }

    /**
     * Adiciona um out á lista
     *
     * @param outputStream
     */
    public void addOutputStream(OutputStream outputStream) {
        Writer ouw = new OutputStreamWriter(outputStream);
        BufferedWriter bfw = new BufferedWriter(ouw);
        this.outs.add(new BufferedWriter(bfw));
    }

    /**
     * Send a message to all the clients
     *
     * @param message
     */
    public void sendMessage(String message) throws IOException {
        System.out.println("Message Handler: Enviar mensagem para todos os outs");
        for (BufferedWriter bw : this.outs) {
            bw.write(message + "\r\n");
            bw.flush();
        }
    }


    public void run() {
    }
}
