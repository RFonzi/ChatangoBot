package io.github.rfonzi.chatangobot.chatango;

public class MessageBuilder extends Message {


    Message message;

    public MessageBuilder(Message message){

        this.message = message;

    }

    public byte[] toBytes(){

        String messageCode =
                "bm:asdf: 0:<n" +
                        message.getNameColorHex() +
                        "/>f x " +
                        message.getFontSize() +
                        message.getTextColorHex() + "=\"" +
                        message.getFont() + "\">" +
                        message.getText() + "\r\n";

        return messageCode.getBytes();
    }
}
