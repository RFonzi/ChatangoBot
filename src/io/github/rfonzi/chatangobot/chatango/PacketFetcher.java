package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.io.InputStream;

public class PacketFetcher implements Runnable {

    private final InputStream in;
    private final PacketTranslator packetTranslator;
    private final Thread fetcherThread;
    private StringBuilder translatedPacket;
    int readByte;


    @Override
    public void run() {

        boolean running = true;

        while(running){
            try {
                readByte = in.read();
            } catch (IOException e) {
                e.printStackTrace();
                running = false;
            }

            if(readByte == -1){ //Socket has been closed
                running = false;
            }
            else if(readByte == 0){
                try {
                    packetTranslator.packetQueue.put(translatedPacket.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                translatedPacket.setLength(0);
            }
            else{
                translatedPacket.append((char)readByte);
            }



        }

    }

    public PacketFetcher(InputStream socketInput, PacketTranslator packetTranslator){
        this.in = socketInput;
        this.translatedPacket = new StringBuilder();
        this.packetTranslator = packetTranslator;

        fetcherThread = new Thread(this);
        fetcherThread.start();

    }

}
