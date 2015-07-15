package io.github.rfonzi.chatangobot.chatango;

public class Message {

    private String sender;
    private String text;
    private String font;

    public Message(){
        
    }

    public String getFont() {
        return font;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }
}
