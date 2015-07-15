package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;

public class PacketTranslator implements Runnable {

    public final Thread translatorThread;
    public ConcurrentLinkedQueue<String> packetQueue;

    @Override
    public void run() {

        boolean running = true;

        while(running){
            if(packetQueue.isEmpty()){
                LockSupport.park();
            }

            System.out.println(packetQueue.poll());
        }

    }

    public PacketTranslator(){
        packetQueue = new ConcurrentLinkedQueue<>();

        translatorThread = new Thread(this);
        translatorThread.start();
    }

}
