package io.github.rfonzi.chatangobot.chatango;

import java.util.ArrayList;

public class RoomInfo {
    private static RoomInfo ourInstance = new RoomInfo();

    public ArrayList<String> modList = new ArrayList<>();

    public static RoomInfo getInstance() {
        return ourInstance;
    }

    private RoomInfo() {
    }
}
