package io.github.rfonzi.chatangobot;

import sun.nio.ch.SocketAdaptor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatangoConnection {

    private String login = ""; //Login goes here
    private String password = ""; //Password goes here
    private String loginURLString = "";
    private String roomURLString = "";

    URL loginURL;
    URL roomURL;
    URLConnection loginConnection;
    URLConnection roomConnection;
    Socket socket;

    public ChatangoConnection() {

    }


    public void setLoginInfo(String login, String password) {

        this.login = login;
        this.password = password;
        this.rebuildURL();
    }

    public String getAuthToken() {
        String cookieHeader = loginConnection.getHeaderFields().get("Set-Cookie").toString();
        String authToken = "";

        Pattern pattern = Pattern.compile("\\Qauth.chatango.com=\\E(.*?);");
        Matcher matcher = pattern.matcher(cookieHeader);

        if(matcher.find()){
            authToken = matcher.group(1);
        }

        return authToken;

    }

    public void joinRoom(String room) throws IOException {
//        roomURLString = "http://www." + room + ".chatango.com/";
//        try {
//            roomURL = new URL(roomURLString);
//            roomConnection = roomURL.openConnection();
//            roomConnection.setDoOutput(true);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println(roomConnection.getHeaderField("Set-Cookie"));

        socket = new Socket("65.chatango.com", 80);
        String tempRoomCode = "bauth:slixtest:1796458324681205:" + this.login + ":" + this.password + ".";
        OutputStream out = socket.getOutputStream();
        out.write(tempRoomCode.getBytes());
    }

    private void rebuildURL() {
        this.loginURLString = "http://www.chatango.com/login?user_id=" + login + "&password=" + password + "&storecookie=on&checkerrors=yes";
    }

    public void connect() {

        if (login == "" || password == "") {
            System.out.println("Login info not set.");
            return;
        } else if (loginURLString == "") {
            System.out.println("URL not build.");
            return;
        }

        try {
            loginURL = new URL(loginURLString);
            loginConnection = loginURL.openConnection();
            loginConnection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
