package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.LinkedBlockingQueue;

public class PacketTranslator implements Runnable {

    public final Thread translatorThread;
    public LinkedBlockingQueue<String> packetQueue;

    @Override
    public void run() {

        boolean running = true;

        while(running){
            try {
                System.out.println(packetQueue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public PacketTranslator(){
        packetQueue = new LinkedBlockingQueue<>();

        translatorThread = new Thread(this);
        translatorThread.start();
    }

}
