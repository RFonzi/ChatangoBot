package io.github.rfonzi.chatangobot.chatango;

import io.github.rfonzi.chatangobot.logging.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class MessageSender {

    Logger log;
    private static MessageSender instance = new MessageSender();
    private SocketInstance socketInstance = SocketInstance.getInstance();
    private OutputStream out = null;

    public static MessageSender getInstance() {
        return instance;
    }

    public void send(String messageCode) {
        if (this.out == null) {
            try {
                this.out = this.socketInstance.socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            log.debug(messageCode);
            out.write(messageCode.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
