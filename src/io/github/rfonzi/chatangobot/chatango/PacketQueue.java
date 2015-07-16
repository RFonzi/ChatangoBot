package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.LinkedBlockingQueue;

public class PacketQueue {

    public LinkedBlockingQueue<String> queue;
    private static PacketQueue instance = null;

    private PacketQueue(){
        queue = new LinkedBlockingQueue<>();
    }

    public static PacketQueue getInstance(){
        if(instance == null){
            instance = new PacketQueue();
        }

        return instance;
    }
}
