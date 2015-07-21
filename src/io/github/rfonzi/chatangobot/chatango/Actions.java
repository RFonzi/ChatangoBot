package io.github.rfonzi.chatangobot.chatango;

import java.util.concurrent.TimeUnit;

public class Actions extends Thread {

    MessageQueue messageQueue;
    Message message;
    String messageText;
    io.github.rfonzi.chatangobot.chatango.State state;


    public Actions() {
        messageQueue = MessageQueue.getInstance();

        state = io.github.rfonzi.chatangobot.chatango.State.getInstance(); //Why??

        state.actionThread = new Thread(this);
        state.actionThread.start();

    }

    @Override
    public void run() {

        while (!state.actionThread.isInterrupted()) {

            try {
                message = messageQueue.queue.take();
                if ( message == null || message.getSender().toLowerCase().equals("slixmachine")) { //Need to remove hardcode
                    continue;
                }
                Message nextMessage = messageQueue.queue.peek();

                if (nextMessage != null &&
                        message.getSender() == nextMessage.getSender() &&
                        message.getTextAsString() == nextMessage.getTextAsString()){ //rudimentary duplicate detection
                    continue;
                }
            } catch (InterruptedException e) {
                break;
            }


            //messageText = message.getText().get(0); //Only support commands in the first font tag (for now)

            for (CommandList c : CommandList.values()) {
                if (c.conditions(message)) {
                    c.doAction(message);
                    break;
                }
            }

            try {
                Thread.sleep(2400); //Prevent flooding, not sure what the best value is
            } catch (InterruptedException e) {
                break;
            }

        }

        System.out.println("||| Actions stopping...");

    }

}
