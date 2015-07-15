package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;

public class Bot {

    public PacketFetcher packetFetcher;
    public PacketTranslator packetTranslator;


    public Bot() throws IOException {

        Connection connection = new Connection();

        connection.setLoginInfo("g6795757", "test"); //test account
        connection.connect();

        System.out.println(connection.getAuthToken());

        connection.joinRoom("slixtest");

        packetFetcher = new PacketFetcher(connection.socket.getInputStream(), packetTranslator);
        packetTranslator = new PacketTranslator();

        packetFetcher.run();
        packetTranslator.run();

    }
}

