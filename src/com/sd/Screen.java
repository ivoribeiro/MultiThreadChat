package com.sd;

import javax.swing.*;
import java.awt.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

class Screen extends Thread {

    private final Queue<String> __messageQueue;
    private final String DEFAULT;
    private final Semaphore __screenSemaphore;
    private JList<String> __messageList;
    private DefaultListModel<String> __listModel;


    /**
     * Wake up the current thread
     */
    public synchronized void wakeUp() {
        this.notify();
    }

    /**
     * Sleep the current thread
     */
    public synchronized void sleep() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param defaultMessage
     */
    public Screen(String defaultMessage) {
        this.__messageQueue = new LinkedBlockingQueue<String>();
        this.__listModel = new DefaultListModel<String>();
        this.__listModel.addElement("teste");
        this.__listModel.addElement("teste");
        this.__listModel.addElement("teste");
        this.__listModel.addElement("teste");
        this.__listModel.addElement("teste");
        this.__messageList = new JList<String>(this.__listModel);
        this.DEFAULT = defaultMessage;
        this.__screenSemaphore = new Semaphore(1);

    }

    /**
     * Adds a message to the queue
     *
     * @param message
     * @param
     */
    public void processMessage(String message) {
        __messageQueue.offer(message);
        this.wakeUp();
    }

    /**
     */
    public void printMessages() {
        try {
            this.__screenSemaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!__messageQueue.isEmpty()) {
            String sm = this.__messageQueue.poll();
            this.__listModel.addElement(sm);
            this.__messageList.setModel(this.__listModel);
        }
        //screenLabel.setText(this.DEFAULT);
        this.__screenSemaphore.release();
        this.sleep();
        this.printMessages();
    }

    /**
     * Build the frame UI
     *
     * @return
     */
    private void buildUI() {
        String myname = Thread.currentThread().getName();
        LayoutBuilder layoutBuilder = new LayoutBuilder(myname, 350, 700);
        layoutBuilder.setPosition(450, 150);
        this.__messageList.setBackground(Color.lightGray);
        this.__messageList.setFixedCellHeight(70);
        this.__messageList.setFixedCellWidth(320);
        JScrollPane pane = new JScrollPane(this.__messageList);
        //pane.setLocation(10,10);
        layoutBuilder.addScrollPane(pane);
        layoutBuilder.build();
        layoutBuilder.dispose();
    }

    /**
     *
     */
    public void run() {
        this.setName("Screen Thread");
        this.buildUI();
        this.sleep();
        this.printMessages();
    }
}
