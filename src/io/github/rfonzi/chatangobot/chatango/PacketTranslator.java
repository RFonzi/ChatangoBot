package io.github.rfonzi.chatangobot.chatango;

import io.github.rfonzi.chatangobot.logging.Logger;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.rfonzi.chatangobot.chatango.Message.Code.*;

public class PacketTranslator implements Runnable {

    PacketQueue packetQueue;
    private String workingString;
    private MessageQueue messageQueue;
    private RoomInfo roomInfo;
    State state;

    public PacketTranslator() {
        packetQueue = PacketQueue.getInstance();
        messageQueue = MessageQueue.getInstance();
        state = State.getInstance();
        roomInfo = RoomInfo.getInstance();


        state.translatorThread = new Thread(this);
        state.translatorThread.start();
    }

    @Override
    public void run() {

        while (!state.translatorThread.isInterrupted()) {
            try {
                workingString = packetQueue.queue.poll(5, TimeUnit.SECONDS);
                if (workingString == null) {
                    continue;
                }
            } catch (InterruptedException e) {
                break;
            }

            if (workingString.startsWith("ok:")) { //Get mod list

                Matcher matcher = Pattern.compile("(?<=:)\\w+").matcher(workingString);

                matcher.find();
                roomInfo.modList.add(matcher.group()); //Add owner

                matcher = Pattern.compile("\\w+(?=,)").matcher(workingString);

                while (matcher.find()) {

                    roomInfo.modList.add(matcher.group()); //Add other mods

                }
            }

            if (workingString.startsWith("b:")) { //Get message
                //i:|timestamp|:|handle|::|first 8 digits of magic number!|:|odd string ending in ==|::0:<f x11="0">|message| #0D #0A #00
                String[] workingStringSplit = workingString.split(":");
                Logger.debug(workingStringSplit[SENDER.ordinal()] + ": " + workingStringSplit[BODY.ordinal()]);

                Message message = new Message();

                message.setTimestamp(workingStringSplit[TIMESTAMP.ordinal()]);
                message.setSender(workingStringSplit[SENDER.ordinal()]);
                message.setBody(workingStringSplit[BODY.ordinal()]);

                try {
                    messageQueue.queue.put(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }

        Logger.info("||| Packet Translator stopping...");

    }

}
