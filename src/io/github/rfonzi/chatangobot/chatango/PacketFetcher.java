package io.github.rfonzi.chatangobot.chatango;

import io.github.rfonzi.chatangobot.logging.Logger;

import java.io.IOException;
import java.io.InputStream;

public class PacketFetcher implements Runnable {

    private final InputStream in;
    private final PacketQueue packetQueue;
    SocketInstance socketInstance;
    int readByte;
    private StringBuilder translatedPacket;
    State state;


    public PacketFetcher() throws IOException {
        socketInstance = SocketInstance.getInstance();
        this.in = socketInstance.socket.getInputStream();

        packetQueue = PacketQueue.getInstance();

        this.translatedPacket = new StringBuilder();

        state = State.getInstance();

        state.fetcherThread = new Thread(this);
        state.fetcherThread.start();

    }

    @Override
    public void run() {

        while (!state.fetcherThread.isInterrupted()) {
            try {
                readByte = in.read();
            } catch (IOException e) {
                state.fetcherThread.interrupt();
                break;
            }

            if (readByte == -1) { //Socket has been closed
                state.running = false;
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

        Logger.info("Socket closed");
        Logger.info("Packet Fetcher stopping...");

        state.translatorThread.interrupt();
        state.actionThread.interrupt();
        state.connectionThread.interrupt();

    }

}
