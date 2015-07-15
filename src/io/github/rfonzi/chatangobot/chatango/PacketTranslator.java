package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.ConcurrentLinkedQueue;

public class PacketTranslator implements Runnable {

    public ConcurrentLinkedQueue<String> packetQueue;

    @Override
    public void run() {

    }

    public PacketTranslator(){
        packetQueue = new ConcurrentLinkedQueue<>();
    }

}
