package io.github.rfonzi.chatangobot.chatango;

import java.util.ArrayList;

enum CommandList implements ICommands{
    SLIX {
        @Override
        public void doAction() {
            messageBuilder.message.setText("God dammit slix this is all your fault");
            messageBuilder.message.setTextColorHex("000000");

            messageSender.send(messageBuilder.toString());

        }

        @Override
        public boolean conditions(String messageText) {
            if(messageText.toLowerCase().startsWith("/slix")){
                System.out.println(">> got slix command");
                return true;
            }

            return false;

        }
    };

    public MessageBuilder messageBuilder = new MessageBuilder();
    public static MessageSender messageSender = MessageSender.getInstance();

}