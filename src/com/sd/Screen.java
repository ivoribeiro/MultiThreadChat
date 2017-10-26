package com.sd;

import javax.swing.*;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

class Screen extends Thread {

    private final Queue<String> __messageQueue;
    private final String DEFAULT;
    private final Semaphore __screenSemaphore;


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
     * @param screenLabel
     */
    public void printMessages(JLabel screenLabel) {
        try {
            this.__screenSemaphore.acquire(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!__messageQueue.isEmpty()) {
            String sm = this.__messageQueue.poll();
            screenLabel.setText(sm);
        }
        screenLabel.setText(this.DEFAULT);
        this.__screenSemaphore.release();
        this.sleep();
        this.printMessages(screenLabel);
    }

    /**
     * Build the frame UI
     *
     * @return
     */
    private JLabel buildUI() {
        String myname = Thread.currentThread().getName();
        LayoutBuilder layoutBuilder = new LayoutBuilder(myname, 500, 75);
        layoutBuilder.setPosition(450, 150);
        JLabel screenLabel = layoutBuilder.label(this.DEFAULT);
        layoutBuilder.addLabel(screenLabel);
        layoutBuilder.build();
        layoutBuilder.dispose();
        return screenLabel;
    }

    /**
     *
     */
    public void run() {
        this.setName("Screen Thread");
        JLabel screenLabel = this.buildUI();
        this.sleep();
        this.printMessages(screenLabel);
    }
}
