package io.github.rfonzi.chatangobot.chatango;

import static io.github.rfonzi.chatangobot.chatango.Message.Code.*;

public class PacketTranslator implements Runnable {

    public final Thread translatorThread;
    PacketQueue packetQueue;
    private String workingString;
    private MessageQueue messageQueue;

    @Override
    public void run() {

        boolean running = true;

        while (running) {
            try {
                workingString = packetQueue.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[] workingStringSplit = workingString.split(":");

            if (workingString.startsWith("b:")) {
                //i:|timestamp|:|handle|::|first 8 digits of magic number!|:|odd string ending in ==|::0:<f x11="0">|message| #0D #0A #00
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

    }

    public PacketTranslator() {
        packetQueue = PacketQueue.getInstance();
        messageQueue = MessageQueue.getInstance();


        translatorThread = new Thread(this);
        translatorThread.start();
    }

}
