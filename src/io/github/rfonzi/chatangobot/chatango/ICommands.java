package io.github.rfonzi.chatangobot.chatango;

public interface ICommands {

    void doAction();

    boolean conditions(String messageText);
}
