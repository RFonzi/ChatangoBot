package io.github.rfonzi.chatangobot.chatango;

public class Actions extends Thread {

    MessageQueue messageQueue;
    Message message;
    Thread actionThread;
    String messageText;


    @Override
    public void run() {

        boolean running = true;

        while(running){

            try {
                message = messageQueue.queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            messageText = message.getText();

            for(CommandList c : CommandList.values()){
                if (c.conditions(messageText)){
                    c.doAction();
                    break;
                }
            }

        }

    }

    public Actions(){
        messageQueue = MessageQueue.getInstance();

        actionThread = new Thread(this);
        actionThread.start();

    }

}
