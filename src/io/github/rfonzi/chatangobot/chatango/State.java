package io.github.rfonzi.chatangobot.chatango;

import java.util.ArrayList;

public class State {
    private static State ourInstance = new State();

    public boolean running = false;

    public Thread fetcherThread;
    public Thread translatorThread;
    public Thread actionThread;
    public Thread connectionThread;

    public ArrayList<Thread> threads;

    public static State getInstance() {
        return ourInstance;
    }

    private State() {
        threads = new ArrayList<>();
        threads.add(fetcherThread);
        threads.add(translatorThread);
        threads.add(actionThread);
        threads.add(connectionThread);
    }
}
