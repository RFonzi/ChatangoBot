package io.github.rfonzi.chatangobot.chatango;

public interface ICommands {

    void doAction(Message message);

    boolean conditions(Message message);
}
