package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.net.Socket;

public class SocketInstance implements IServer {

    public Socket socket = null;
    private static SocketInstance instance = new SocketInstance();

    private SocketInstance(){
    }

    public static SocketInstance getInstance(){
        return instance;
    }

    public void connect() throws IOException {
        if(socket == null){
            socket = new Socket(serverName, port);
        }
    }
}
