package io.github.rfonzi.chatangobot.chatango;

public class Actions extends Thread {

    MessageQueue messageQueue;
    Message message;
    Thread actionThread;
    Commands commands;


    @Override
    public void run() {

        boolean running = true;

        while(running){

            try {
                message = messageQueue.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(message.getSender() + ": " + message.getText() + "  | " + message.getTimestamp());
            System.out.println("[font = " + message.getFont() + " | color = #" + message.getColorHex() + " | size = " + message.getFontSize() + "]");

        }

    }

    public Actions(){
        messageQueue = MessageQueue.getInstance();



        actionThread = new Thread(this);
        actionThread.start();
    }

}
