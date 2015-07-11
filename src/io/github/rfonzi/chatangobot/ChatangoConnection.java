package io.github.rfonzi.chatangobot;

import com.sun.deploy.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatangoConnection {

    private static String login = ""; //Login goes here
    private static String password = ""; //Password goes here
    private static String urlString = "";

    URL url;
    URLConnection connection;

    public ChatangoConnection() {

    }


    public void setLoginInfo(String login, String password) {

        this.login = login;
        this.password = password;
        this.rebuildURL();
    }

    public String getAuthToken() {
        String cookieHeader = connection.getHeaderFields().get("Set-Cookie").toString();
        String authToken = "";

        Pattern pattern = Pattern.compile("\\Qauth.chatango.com=\\E(.*?);");
        Matcher matcher = pattern.matcher(cookieHeader);

        if(matcher.find()){
            authToken = matcher.group(1);
        }

        return authToken;

    }

    private void rebuildURL() {
        this.urlString = "http://www.chatango.com/login?user_id=" + login + "&password=" + password + "&storecookie=on&checkerrors=yes";
    }

    public void connect() {

        if (login == "" || password == "") {
            System.out.println("Login info not set.");
            return;
        } else if (urlString == "") {
            System.out.println("URL not build.");
            return;
        }

        try {
            url = new URL(urlString);
            connection = url.openConnection();
            connection.setDoOutput(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
