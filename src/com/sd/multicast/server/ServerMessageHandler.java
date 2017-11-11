package com.sd.multicast.server;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
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
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(message);
        byte[] buf = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
    }

    /**
     * Send frame to all the group
     *
     * @throws IOException
     */
    public void sendFrame(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(image);
        byte[] buf = baos.toByteArray();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
    }

}
