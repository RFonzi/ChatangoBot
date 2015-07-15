package io.github.rfonzi.chatangobot.chatango;

public class Message {

    private String sender;
    private String text;
    private String font;
    private int fontSize;
    private String timestamp;
    private String colorHex;


    public Message(){

    }

    public String getFont() {
        return font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public String getSender() {
        return sender;
    }

    public String getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setFont(String font) {

        switch(font){
            case Fonts.ARIAL:
                font = "Arial";
                break;
            case Fonts.COMIC:
                font = "Comic Sans";
                break;
            case Fonts.GEORGIA:
                font = "Georgia";
                break;
            case Fonts.HANDWRITING:
                font = "Handwriting";
                break;
            case Fonts.IMPACT:
                font = "Impact";
                break;
            case Fonts.PALATINO:
                font = "Palatino";
                break;
            case Fonts.PAPYRUS:
                font = "Papyrus";
                break;
            case Fonts.TIMES:
                font = "Times New Roman";
                break;
            case Fonts.TYPEWRITER:
                font = "Typewriter";
                break;
        }


        this.font = font;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setColorHex(String colorHex) {
        if (colorHex == null){
            colorHex = "FFFFFF";
        }
        this.colorHex = colorHex;
    }
}
