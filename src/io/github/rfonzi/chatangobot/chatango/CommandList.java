package io.github.rfonzi.chatangobot.chatango;

import java.util.ArrayList;

enum CommandList implements ICommands{
    EXAMPLE {
        @Override
        public void doAction() {
            messageBuilder.message.setText("Hi");

            messageSender.send(messageBuilder.toString());

        }

        @Override
        public boolean conditions(String messageText) {
            if(messageText.toLowerCase().contains("hi")){
                System.out.println(">> got hi command");
                return true;
            }

            return false;

        }
    };

    public MessageBuilder messageBuilder = new MessageBuilder();
    public static MessageSender messageSender = MessageSender.getInstance();

}