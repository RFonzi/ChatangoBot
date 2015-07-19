package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.TimeUnit;

import static io.github.rfonzi.chatangobot.chatango.Message.Code.*;

public class PacketTranslator implements Runnable {

    PacketQueue packetQueue;
    private String workingString;
    private MessageQueue messageQueue;
    State state;

    public PacketTranslator() {
        packetQueue = PacketQueue.getInstance();
        messageQueue = MessageQueue.getInstance();

        state = State.getInstance();

        state.translatorThread = new Thread(this);
        state.translatorThread.start();
    }

    @Override
    public void run() {

        while (!state.translatorThread.isInterrupted()) {
            try {
                workingString = packetQueue.queue.poll(5, TimeUnit.SECONDS);
                if(workingString == null){
                    continue;
                }
            } catch (InterruptedException e) {
                break;
            }

            if (workingString.startsWith("b:")) {
                //i:|timestamp|:|handle|::|first 8 digits of magic number!|:|odd string ending in ==|::0:<f x11="0">|message| #0D #0A #00
                String[] workingStringSplit = workingString.split(":");
                System.out.println(">> " + workingString);

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

        System.out.println("||| Packet Translator stopping...");

    }

}
