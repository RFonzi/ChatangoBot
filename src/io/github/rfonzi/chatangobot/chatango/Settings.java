package io.github.rfonzi.chatangobot.chatango;

import io.github.rfonzi.chatangobot.logging.Logger;

import java.io.*;
import java.util.Properties;

public class Settings {

    FileOutputStream output;
    FileInputStream input;
    File file;
    Properties prop;
    public String accountName;
    public String password;
    public String roomName;

    public Settings(String filename) {
        prop = new Properties();

        try {
            file = new File(filename);
            if(!file.createNewFile())
                Logger.info("Settings file not found. Creating...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void set(String accountName, String password, String roomName){
        this.accountName = accountName;
        this.password = password;
        this.roomName = roomName;
    }

    public void save(){

        file.setWritable(true);

        try {
            output = new FileOutputStream(file);

            prop.setProperty("account_name", this.accountName);
            prop.setProperty("password", this.password);
            prop.setProperty("room_name", this.roomName);

            prop.store(output, null);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        file.setWritable(false);
    }

    public void load(){
        String accountName;
        String password;
        String roomName;

        file.setReadable(true);

        try {
            input = new FileInputStream(file);

            prop.load(input);

            accountName = prop.getProperty("account_name");
            password = prop.getProperty("password");
            roomName = prop.getProperty("room_name");

            set(accountName, password, roomName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        file.setReadable(false);

    }

//    private void open(){
//        file = new File(filename + ".ini");
//
//        try {
//            if(!file.createNewFile()){
//                Logger.info("Settings file doesn't exist yet. Creating...");
//                set("", "", "");
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
