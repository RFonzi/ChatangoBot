package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;

public class Bot {


    public Bot() throws IOException {

        Connection connection = new Connection();

        connection.setLoginInfo("g6795757", "test"); //test account
        connection.connect();

        System.out.println(connection.getAuthToken());

        connection.joinRoom("slixtest");

        PacketFetcher packetFetcher = new PacketFetcher(connection.socket.getInputStream());
        packetFetcher.run();

    }
}

