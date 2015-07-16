package io.github.rfonzi.chatangobot.chatango;

public enum Commands implements ICommands{
    SAMPLE("hi") {
        @Override
        public void doAction() {

        }
    };


    private String command;

    private Commands(String command){
        this.command = command;
    }


}
