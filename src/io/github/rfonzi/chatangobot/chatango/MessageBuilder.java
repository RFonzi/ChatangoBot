package io.github.rfonzi.chatangobot.chatango;

public class MessageBuilder extends Message {


    Message message;

    public MessageBuilder() {
        this.message = new Message();
    }

    public MessageBuilder(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {

        String messageCode = "bm:asdf: 0:<n" +
                message.getNameColor() +
                "/>";

        for (int i = 0; i < message.getFont().size(); i++) {

            if (!(message.getFontSize().get(i).isEmpty() && message.getFontColor().get(i).isEmpty() && message.getFont().get(i).isEmpty())){

                messageCode = messageCode.concat("<f  x" +
                        message.getFontSize().get(i) +
                        message.getFontColor().get(i) +
                        "=\"" +
                        message.getFont().get(i) +
                        "\">");
            }


            messageCode = messageCode.concat(message.getText().get(i));

        }

        messageCode = messageCode.concat("\r\n");


        return messageCode;
    }

    public void insertFontTag(String fontSize, String fontColor, String font, String text) {
        message.setFontSize(fontSize);
        message.setFontColor(fontColor);
        message.setFont(font);
        message.setText(text);
    }

//    public void extractDetails(Message message){
//        this.message.setSender(message.getSender());
//        this.message.setTimestamp(message.getTimestamp());
//        this.message.setFontSize(message.getFontSize());
//        this.message.setFont(message.getFont());
//        this.message.setText(message.getText());
//        this.message.setNameColor(message.getNameColor());
//        this.message.setFontColor(message.getFontColor());
//    }
}
