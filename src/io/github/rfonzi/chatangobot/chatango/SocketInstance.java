package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.net.Socket;

public class SocketInstance {

    private static SocketInstance instance = new SocketInstance();
    public Socket socket = null;

    private SocketInstance() {
    }

    public static SocketInstance getInstance() {
        return instance;
    }

    public void connect(String serverName) throws IOException {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(serverName, 443);
            State.getInstance().running = true;
        }

    }
}
