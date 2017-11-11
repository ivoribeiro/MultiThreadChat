package com.sd.multicast.client;

import com.sd.camera.Camera;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class MulticastChatClient extends Thread {

    private Socket serverSocket;
    private MulticastSocket multicastSocket;
    private InetAddress group;
    private String HOST = "localhost";


    public static void main(String[] args) throws IOException {
        new MulticastChatClient().start();
    }

    private MulticastChatClient() throws IOException {
        this.multicastSocket = new MulticastSocket(4446);
        this.serverSocket = new Socket(this.HOST, 2048);
        this.group = InetAddress.getByName("230.0.0.1");
        multicastSocket.joinGroup(this.group);
    }

    public void run() {
        new MulticastChatClientListner(this.multicastSocket).start();
        try {
            new MulticastChatClientWriter(this.serverSocket.getOutputStream()).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() throws IOException {
        this.multicastSocket.leaveGroup(this.group);
        this.multicastSocket.close();
    }

}

class MulticastChatClientListner extends Thread {

    private MulticastSocket multicastSocket;

    public MulticastChatClientListner(MulticastSocket multicastSocket) {
        this.multicastSocket = multicastSocket;
    }

    public void run() {
        DatagramPacket packet;
        while (true) {
            byte[] buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            try {
                this.multicastSocket.receive(packet);
                //Deserialze object
                ByteArrayInputStream bais = new ByteArrayInputStream(buf);
                ObjectInputStream ois = new ObjectInputStream(bais);
                try {
                    Object readObject = ois.readObject();
                    if (readObject instanceof String) {
                        String message = (String) readObject;
                        System.out.println("Message is: " + message);
                    } else {
                        System.out.println("The received object is not of type String!");
                    }
                } catch (Exception e) {
                    System.out.println("No object could be read from the received UDP datagram.");
                }
                String received = new String(packet.getData(), 0, packet.getLength());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class MulticastChatClientWriter extends Thread {

    private PrintWriter out = null;

    public MulticastChatClientWriter(OutputStream out) {
        this.out = new PrintWriter(out, true);
    }

    public void run() {
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        System.out.println("User Input:");
        Camera camera = new Camera(out);
        camera.start();
        //while ((userInput = stdIn.readLine()) != null) {
        //    out.println(userInput);
        //}
        System.out.println("OUT CLOSE");
        out.close();

    }
}
