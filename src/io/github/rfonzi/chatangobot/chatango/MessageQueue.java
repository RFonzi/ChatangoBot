package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

    public LinkedBlockingQueue<Message> queue;
    private static MessageQueue instance = null;

    private MessageQueue(){
        queue = new LinkedBlockingQueue<>();
    }

    public static MessageQueue getInstance(){
        if(instance == null){
            instance = new MessageQueue();
        }

        return instance;
    }
}
