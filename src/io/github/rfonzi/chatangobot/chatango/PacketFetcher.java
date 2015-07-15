package io.github.rfonzi.chatangobot.chatango;

import com.sun.xml.internal.bind.v2.runtime.reflect.Lister;

import java.io.IOException;
import java.io.InputStream;

public class PacketFetcher implements Runnable {

    private final InputStream in;
    private final PacketTranslator packetTranslator;
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
            if(readByte == 0){
                packetTranslator.packetQueue.add(translatedPacket.toString());
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

    }

}
