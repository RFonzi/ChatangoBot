package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.io.InputStream;

public class PacketFetcher implements Runnable {

    private final InputStream in;
    private final PacketQueue packetQueue;
    SocketInstance socketInstance;
    int readByte;
    private StringBuilder translatedPacket;


    public PacketFetcher() throws IOException {
        socketInstance = SocketInstance.getInstance();
        this.in = socketInstance.socket.getInputStream();

        packetQueue = PacketQueue.getInstance();

        this.translatedPacket = new StringBuilder();

        Thread fetcherThread = new Thread(this);
        fetcherThread.start();

    }

    @Override
    public void run() {

        boolean running = true;

        while (running) {
            try {
                readByte = in.read();
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }

            if (readByte == -1) { //Socket has been closed
                running = false;
            } else if (readByte == 0) {
                try {
                    packetQueue.queue.put(translatedPacket.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                translatedPacket.setLength(0);
            } else {
                translatedPacket.append((char) readByte);
            }


        }

    }

}
