package io.github.rfonzi.chatangobot.chatango;

public class Actions extends Thread {

    MessageQueue messageQueue;
    Message message;
    Thread actionThread;
    String messageText;


    public Actions() {
        messageQueue = MessageQueue.getInstance();

        actionThread = new Thread(this);
        actionThread.start();

    }

    @Override
    public void run() {

        boolean running = true;

        while (running) {

            try {
                message = messageQueue.queue.take();
                if (message.getSender().equals("g6795757")) { //Need to remove hardcode
                    continue;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            messageText = message.getText().get(0); //Only support commands in the first font tag (for now)

            for (CommandList c : CommandList.values()) {
                if (c.conditions(messageText)) {
                    c.doAction();
                    break;
                }
            }

        }

    }

}
