package io.github.rfonzi.chatangobot.chatango;

import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Connection implements Runnable {

    URL loginURL;
    URLConnection loginConnection;
    SocketInstance socketInstance;
    State state;
    OutputStream out;
    private String login = ""; //Login goes here
    private String password = ""; //Password goes here
    private String loginURLString = "";

    public Connection() {
        socketInstance = SocketInstance.getInstance();

        try {
            socketInstance.connect();
            out = socketInstance.socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        state = State.getInstance();
        state.connectionThread = new Thread(this);
        state.connectionThread.start();


        try {
            PacketTranslator packetTranslator = new PacketTranslator();
            PacketFetcher packetFetcher = new PacketFetcher();
            Actions actions = new Actions();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    public void run() {

        while (!state.connectionThread.isInterrupted()) {

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
                break;
            }

        }

        System.out.println("||| Connection stopping...");

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

        if (matcher.find()) {
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

        if (login.equals("") || password.equals("")) {
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
