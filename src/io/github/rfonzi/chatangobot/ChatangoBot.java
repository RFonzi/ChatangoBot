package io.github.rfonzi.chatangobot;


import java.io.*;

import java.net.*;
import java.util.Map;
import java.util.Set;

public class ChatangoBot {

    URL url;
    URLConnection connection;

    private static String login = ""; //Login goes here
    private static String password = ""; //Password goes here
    private static String urlString = "http://www.chatango.com/login?user_id=" + login + "&password=" + password + "&storecookie=on&checkerrors=yes";

    public ChatangoBot() {
        System.out.println(urlString);

        try {
            url = new URL(urlString);
            connection = url.openConnection();
            connection.setDoOutput(true);

        } catch (IOException e) {
            e.printStackTrace();
        }


        Map map = connection.getHeaderFields();

        System.out.println(map.get("Set-Cookie").toString());

    }
}

