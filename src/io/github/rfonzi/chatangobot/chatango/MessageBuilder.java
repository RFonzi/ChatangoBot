package io.github.rfonzi.chatangobot.chatango;

public class MessageBuilder extends Message {


    Message message;

    public MessageBuilder(){
        this.message = new Message();
    }

    public MessageBuilder(Message message){
        this.message = message;
    }

    @Override
    public String toString(){

        String messageCode =
                "bm:asdf: 0:<n" +
                        message.getNameColorHex() +
                        "/><f x" +
                        message.getFontSize() +
                        message.getTextColorHex() + "=\"" +
                        message.getFont() + "\">" +
                        message.getText() + "\r\n";

        return messageCode;
    }

    public void extractDetails(Message message){
        this.message.setSender(message.getSender());
        this.message.setTimestamp(message.getTimestamp());
        this.message.setFontSize(message.getFontSize());
        this.message.setFont(message.getFont());
        this.message.setText(message.getText());
        this.message.setNameColorHex(message.getNameColorHex());
        this.message.setTextColorHex(message.getTextColorHex());
    }
}
