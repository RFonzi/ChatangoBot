package io.github.rfonzi.chatangobot.chatango;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public enum Code{
        MESSAGE_TYPE,
        TIMESTAMP,
        SENDER,
        UNKNOWN1,
        MAGIC_NO1,
        UNKNOWN2,
        MAGIC_NO2,
        UNKNOWN3,
        UNKNOWN4,
        UNKNOWN5,
        BODY;
    }

    private String sender;
    private ArrayList<String> text;
    private ArrayList<String> font;
    private ArrayList<String> fontSize;
    private String timestamp;
    private ArrayList<String> fontColor;
    private String nameColor;

    private int totalFontTags;


    public Message(){
        text = new ArrayList<>();
        font = new ArrayList<>();
        fontSize = new ArrayList<>();
        fontColor = new ArrayList<>();

        setDefaults();

    }

    public void setDefaults(){
        setNameColor("000");
    }

    public ArrayList<String> getFont() {
        return font;
    }

    public ArrayList<String> getFontSize() {
        return fontSize;
    }

    public String getSender() {
        return sender;
    }

    public ArrayList<String> getText() {
        return text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public ArrayList<String> getFontColor() {
        return fontColor;
    }

    public String getNameColor() {
        return nameColor;
    }

    public int getTotalFontTags() {
        return totalFontTags;
    }

    public void setFont(String font) {

        if(font.isEmpty() && this.font.size() != 0){
            font = this.font.get(this.font.size() - 1); //if it's empty, get the last size value
        }

        switch(font){
            case Fonts.ARIAL_FLASH:
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



        this.font.add(font);
    }

    public void setFontSize(String fontSize) {
        if(fontSize.length() == 1){
            fontSize = "0" + fontSize;
        }

        if(fontSize.isEmpty() && this.fontSize.size() != 0){
            fontSize = this.fontSize.get(this.fontSize.size() - 1); //if it's empty, get the last size value
        }

        this.fontSize.add(fontSize);

    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setText(String text) {
        this.text.add(text);
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setFontColor(String fontColor) {
        if(fontColor.isEmpty() && this.fontColor.size() != 0){
            fontColor = this.fontColor.get(this.fontColor.size() - 1); //if it's empty, get the last color value
        }
        this.fontColor.add(fontColor);
    }

    public void setNameColor(String nameColor) {
        this.nameColor = nameColor;
    }

    public void setBody(String body){
        Pattern nameColorPattern = Pattern.compile("(?<=<n)\\w+");
        Pattern fontSizeAndColorPattern = Pattern.compile("(?<=<f x)\\w*");
        Pattern fontPattern = Pattern.compile("(?<==\")[\\w ]*");
        Pattern textPattern = Pattern.compile("(?<=\">)[\\w\\s]+");


        Matcher matcher = nameColorPattern.matcher(body);

        if(matcher.find()){
            this.setNameColor(matcher.group());
        } else {
            this.setNameColor("");
        }

        matcher = fontSizeAndColorPattern.matcher(body);

        while (matcher.find()) {
            String fontSizeAndColor = matcher.group();

            if(fontSizeAndColor.length() == 0){ //Unchanged
                this.setFontSize("");
                this.setFontColor("");
            }
            else if(fontSizeAndColor.length() == 3 || fontSizeAndColor.length() == 6){ //JUST a 3 char hex or 6 char hex
                this.setFontSize("");
                this.setFontColor(fontSizeAndColor);
            }
            else if(fontSizeAndColor.length() == 2){ //JUST font size
                this.setFontSize(fontSizeAndColor);
                this.setFontColor("");
            }
            else if(fontSizeAndColor.length() == 5 || fontSizeAndColor.length() == 8){ //BOTH of the above
                this.setFontSize(fontSizeAndColor.substring(0, 2));
                this.setFontColor(fontSizeAndColor.substring(2));
            }
        }

        matcher = fontPattern.matcher(body);

        while(matcher.find()){
            this.setFont(matcher.group());
        }

        matcher = textPattern.matcher(body);
        while(matcher.find()){
            this.setText(matcher.group());
        }

        this.totalFontTags = this.fontColor.size();


        //Need to replace regex with something else

    }
}




