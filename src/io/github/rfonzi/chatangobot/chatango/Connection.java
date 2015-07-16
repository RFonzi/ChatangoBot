package io.github.rfonzi.chatangobot.chatango;

import java.io.*;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection implements Runnable {

    private String login = ""; //Login goes here
    private String password = ""; //Password goes here
    private String loginURLString = "";

    URL loginURL;
    URLConnection loginConnection;
    SocketInstance socketInstance;
    Thread connectionThread;
    OutputStream out;

    @Override
    public void run() {

        boolean running = true;



        while(running){

            try {
                out.write("\r\n\0".getBytes());
                out.flush();
                System.out.println("<< \\r\\n\\0 KEEPALIVE");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public Connection() {
        socketInstance = SocketInstance.getInstance();

        try {
            socketInstance.connect();
            out = socketInstance.socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        connectionThread = new Thread(this);
        connectionThread.start();


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
        socketInstance.connect();

        String tempRoomCode = "bauth:" + room + "::" + this.login + ":" + this.password + "\0";
        OutputStream out = socketInstance.socket.getOutputStream();
        //out.write("v\0".getBytes());  //Might not need this
        out.write(tempRoomCode.getBytes());

        out.flush();

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
