package com.sd.camera;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;


public class Camera extends Thread {

    private File file;
    private IMediaWriter writer;
    private Dimension size;
    private Webcam webcam;
    private boolean recording = false;

    public static void main(String[] args) throws Throwable {
        new Camera().start();
    }

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

    public Camera() {
        this.recording = true;
        this.file = new File("output.ts");
        this.writer = ToolFactory.makeWriter(file.getName());
        this.size = WebcamResolution.QVGA.getSize();
        this.writer.addVideoStream(0, 0, ICodec.ID.CODEC_ID_H264, size.width, size.height);
        this.webcam = Webcam.getDefault();
        webcam.setViewSize(this.size);
        webcam.open(true);
    }


    public void run() {
        long start = System.currentTimeMillis();
        int i = 0;
        while (recording) {
            System.out.println("Capture frame " + i);
            BufferedImage image = ConverterFactory.convertToType(this.webcam.getImage(), BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter = ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);
            IVideoPicture frame = converter.toPicture(image, (System.currentTimeMillis() - start) * 1000);
            frame.setKeyFrame(i == 0);
            frame.setQuality(10);
            writer.encodeVideo(0, frame);
            i++;
            // 10 FPS
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        System.out.println("Video recorded in file: " + file.getAbsolutePath());
    }


}