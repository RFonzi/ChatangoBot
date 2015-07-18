package io.github.rfonzi.chatangobot.chatango;

import java.util.Random;

enum CommandList implements ICommands {
    EXAMPLE {
        @Override
        public void doAction(Message message) {

            messageBuilder.insertFontTag("12", "2D3", "Courier New", "Hi");
            messageSender.send(messageBuilder.toString());
            messageBuilder.message.clear();

        }

        @Override
        public boolean conditions(String messageText) {
            if (messageText.toLowerCase().contains("hi")) {
                System.out.println(">> got hi command");
                return true;
            }

            return false;

        }
    };

    public static MessageSender messageSender = MessageSender.getInstance();
    public MessageBuilder messageBuilder = new MessageBuilder();

}