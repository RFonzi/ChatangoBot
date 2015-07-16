package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;

public class Bot {

    public PacketFetcher packetFetcher;
    public PacketTranslator packetTranslator;
    public Actions actions;



    public Bot() throws IOException {

        Connection connection = new Connection();

        connection.setLoginInfo("g6795757", "test"); //test account
        connection.connect();

        System.out.println(connection.getAuthToken());

        connection.joinRoom("slixtest");

        packetTranslator = new PacketTranslator();
        packetFetcher = new PacketFetcher ();
        actions = new Actions();


    }
}

