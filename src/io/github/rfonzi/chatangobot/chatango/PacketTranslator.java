package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PacketTranslator implements Runnable {

    public final Thread translatorThread;
    private final Pattern fontSizePattern;
    private final Pattern timestampPattern;
    private final Pattern senderPattern;
    private final Pattern fontPattern;
    private final Pattern messagePattern;
    private final Pattern fontColorPattern;
    Matcher matcher;
    public LinkedBlockingQueue<String> packetQueue;
    private String workingString;
    private MessageQueue messageQueue;

    @Override
    public void run() {

        boolean running = true;

        while (running) {
            try {
                workingString = packetQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (workingString.startsWith("i:") || workingString.startsWith("b:")) {
                //i:|timestamp|:|handle|::|first 8 digits of magic number!|:|odd string ending in ==|::0:<f x11="0">|message| #0D #0A #00

                Message message = new Message();
                matcher = timestampPattern.matcher(workingString);
                if (matcher.find()) {
                    message.setTimestamp(matcher.group());
                }


                matcher = senderPattern.matcher(workingString);
                if (matcher.find()) {
                    message.setSender(matcher.group());
                }


                matcher = messagePattern.matcher(workingString);
                if (matcher.find()) {
                    message.setText(matcher.group());
                }


                matcher = fontSizePattern.matcher(workingString);
                if(matcher.find()){
                    message.setFontSize(Integer.parseInt(matcher.group()));
                }


                matcher = fontPattern.matcher(workingString);
                if(matcher.find()){
                    message.setFont(matcher.group());
                }


                matcher = fontColorPattern.matcher(workingString);
                if(matcher.find()){
                    message.setColorHex(matcher.group());
                }


                try {
                    messageQueue.queue.put(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

    }

    public PacketTranslator() {
        packetQueue = new LinkedBlockingQueue<>();
        messageQueue = MessageQueue.getInstance();

        timestampPattern = Pattern.compile("(?<=:)\\d+"); //matching timestamp
        senderPattern = Pattern.compile("(?<=\\d:)\\w+(?=:)"); //matching sender
        fontSizePattern = Pattern.compile("(?<=<f x)\\d{2}"); //matching font size
        fontPattern = Pattern.compile("(?<=\").*(?=\">)");
        messagePattern = Pattern.compile("(?<=\">).*$"); //matching message contents
        fontColorPattern = Pattern.compile("(?<=<f x\\d{2})\\w+"); //matching color hex


        translatorThread = new Thread(this);
        translatorThread.start();
    }

}
