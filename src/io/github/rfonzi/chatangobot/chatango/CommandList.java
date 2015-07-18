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
    },
    ROLL {
        @Override
        public void doAction(Message message) {
            Random random = new Random();

            int roll = random.nextInt(100) + 1;

            if(roll == 22){
                messageBuilder.insertFontTag("12", "2D3", "Courier New", "@" + message.getSender() + " rolled 22 " + " \rhttp://i.imgur.com/PWs2oSO.png");
            } else if (roll % 11 == 0 || roll == 100){
                messageBuilder.insertFontTag("12", "2D3", "Courier New", "@" + message.getSender() + " rolled " + roll + " \rhttp://i.imgur.com/IzO1LYs.png");
            } else {
                messageBuilder.insertFontTag("12", "2D3", "Courier New", "@" + message.getSender() + " rolled " + roll);
            }

            messageSender.send(messageBuilder.toString());
            messageBuilder.message.clear();
        }

        @Override
        public boolean conditions(String messageText) {
            if (messageText.toLowerCase().startsWith("/roll")) {
                System.out.println(">> got roll command");
                return true;
            }

            return false;
        }
    };

    public static MessageSender messageSender = MessageSender.getInstance();
    public MessageBuilder messageBuilder = new MessageBuilder();

}