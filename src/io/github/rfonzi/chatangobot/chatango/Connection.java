package io.github.rfonzi.chatangobot.chatango;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import io.github.rfonzi.chatangobot.logging.Logger;
import jdk.nashorn.internal.parser.JSONParser;
import sun.rmi.runtime.Log;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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
    private String weightsString = "[[\"5\", 75], [\"6\", 75], [\"7\", 75], [\"8\", 75], [\"16\", 75], [\"17\", 75], " +
            "[\"18\", 75], [\"9\", 95], [\"11\", 95], [\"12\", 95], [\"13\", 95], [\"14\", 95], [\"15\", 95], " +
            "[\"19\", 110], [\"23\", 110], [\"24\", 110], [\"25\", 110], [\"26\", 110], [\"28\", 104], " +
            "[\"29\", 104], [\"30\", 104], [\"31\", 104], [\"32\", 104], [\"33\", 104], [\"35\", 101], " +
            "[\"36\", 101], [\"37\", 101], [\"38\", 101], [\"39\", 101], [\"40\", 101], [\"41\", 101], " +
            "[\"42\", 101], [\"43\", 101], [\"44\", 101], [\"45\", 101], [\"46\", 101], [\"47\", 101], " +
            "[\"48\", 101], [\"49\", 101], [\"50\", 101], [\"52\", 110], [\"53\", 110], [\"55\", 110], " +
            "[\"57\", 110], [\"58\", 110], [\"59\", 110], [\"60\", 110], [\"61\", 110], [\"62\", 110], " +
            "[\"63\", 110], [\"64\", 110], [\"65\", 110], [\"66\", 110], [\"68\", 95], [\"71\", 116], " +
            "[\"72\", 116], [\"73\", 116], [\"74\", 116], [\"75\", 116], [\"76\", 116], [\"77\", 116], " +
            "[\"78\", 116], [\"79\", 116], [\"80\", 116], [\"81\", 116], [\"82\", 116], [\"83\", 116], [\"84\", 116]]";

    public Connection() {

        socketInstance = SocketInstance.getInstance();
        state = State.getInstance();

    }

    @Override
    public void run() {

        try {
            out = socketInstance.socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (!state.connectionThread.isInterrupted()) {

            try {
                out.write("\r\n\0".getBytes());
                out.flush();
                Logger.info("KEEPALIVE");
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                break;
            }

        }

        Logger.info("Connection stopping...");

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

    public void setRoom(String room) throws IOException {

        socketInstance.connect("s" + getServerId(room) + ".chatango.com");

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
            Logger.warn("Login info not set.");
            return;
        } else if (loginURLString == "") {
            Logger.warn("URL not build.");
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

    //Got getServerId from https://github.com/lenis0012/chatango-java-api
    //Thanks whoever figured this mess out
    private int getServerId(String name) {
        final List<Entry<String, Integer>> weights = new ArrayList<>();

        JsonArray list = new JsonParser().parse(this.weightsString).getAsJsonArray();
        for(int i = 0; i < list.size(); i++) {
            JsonArray entry = list.get(i).getAsJsonArray();
            weights.add(new AbstractMap.SimpleEntry<>(entry.get(0).getAsString(), entry.get(1).getAsInt()));
        }

        name = name.toLowerCase().replaceAll("[^0-9a-z]", "q");
        float fnv = (float) new BigInteger(name.substring(0, Math.min(5, name.length())), 36).intValue();
        int lnv = 1000;
        if(name.length() > 6) {
            lnv = new BigInteger(name.substring(6, 6 + Math.min(3, name.length() - 6)), 36).intValue();
            lnv = Math.max(lnv, 1000);
        }
        float num = (fnv % lnv) / lnv;
        int maxnum = weights.stream().mapToInt(Entry::getValue).sum();
        float sumfreq = 0;
        int sn = 0;
        for(Entry<String, Integer> entry : weights) {
            sumfreq += ((float) entry.getValue()) / maxnum;
            if(num <= sumfreq) {
                sn = Integer.parseInt(entry.getKey());
                break;
            }
        }
        return sn;
    }


    public void start(){
        connect();



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

    public void stop(){
        try {
            SocketInstance socketInstance = SocketInstance.getInstance();
            socketInstance.socket.close();
            state.running = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
