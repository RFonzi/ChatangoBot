package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.io.OutputStream;

public class MessageSender {


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
            System.out.println("<< " + messageCode);
            out.write(messageCode.getBytes());
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
