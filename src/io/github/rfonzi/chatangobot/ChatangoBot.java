package io.github.rfonzi.chatangobot;


import java.io.IOException;

public class ChatangoBot {


    public ChatangoBot() throws IOException {

        ChatangoConnection chatangoConnection = new ChatangoConnection();

        chatangoConnection.setLoginInfo("g6795757", "test"); //test account
        chatangoConnection.connect();

        System.out.println(chatangoConnection.getAuthToken());

        chatangoConnection.joinRoom("slixtest");

    }
}

