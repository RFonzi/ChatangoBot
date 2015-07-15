package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.io.InputStream;

public class PacketFetcher implements Runnable {

    private final InputStream in;
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

            }
            else{
                translatedPacket.append((char)readByte);
            }

        }

    }

    public PacketFetcher(InputStream socketInput){
        this.in = socketInput;
        translatedPacket = new StringBuilder();
    }

}
