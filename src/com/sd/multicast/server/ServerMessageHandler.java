package com.sd.multicast.server;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;

public class ServerMessageHandler {
    DatagramSocket socket = null;
    InetAddress group = null;

    public ServerMessageHandler(DatagramSocket socket, InetAddress group) {
        this.socket = socket;
        this.group = group;
    }

    /**
     * Send a message to all the clients
     *
     * @param message
     */
    public void sendMessage(String message) throws IOException {
        byte[] buf = new byte[256];
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
    }

}
