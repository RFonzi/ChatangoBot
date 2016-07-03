package io.github.rfonzi.chatangobot.chatango;

import io.github.rfonzi.chatangobot.logging.Logger;

import java.util.Random;

enum CommandList implements ICommands {
    //    EXAMPLE {
//        @Override
//        public void doAction(Message message) {
//
//            messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "Hi " + message.getSender() + "!!!");
//            messageSender.send(messageBuilder.toString());
//            messageBuilder.message.clear();
//
//        }
//
//        @Override
//        public boolean conditions(Message message) {
//            if (message.getTextAsString().toLowerCase().startsWith("hi")) {
//                Logger.debug("Got hi command");
//                return true;
//            }
//
//            return false;
//
//        }
//    },
    ROLL {
        @Override
        public void doAction(Message message) {
            Random random = new Random();

            int roll = random.nextInt(100) + 1;

            if (roll == 22) {
                messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "@" + message.getSender() + " rolled 22 " + " http://i.imgur.com/PWs2oSO.png");
            } else if (roll % 11 == 0 || roll == 100) {
                messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "@" + message.getSender() + " rolled " + roll + " http://i.imgur.com/IzO1LYs.png");
            } else {
                messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "@" + message.getSender() + " rolled " + roll);
            }

            messageSender.send(messageBuilder.toString());
            messageBuilder.message.clear();
        }

        @Override
        public boolean conditions(Message message) {
            if (message.getTextAsString().toLowerCase().startsWith("/roll")) {
                Logger.debug("Got roll command");
                return true;
            }

            return false;
        }
    },

    USA {
        @Override
        public void doAction(Message message) {

            Random random = new Random();
            int roll = random.nextInt(6) + 1;

            if (roll == 6) {
                messageBuilder.insertFontTag(message.defaultFontSize, "F00", message.defaultFontFace, "VIVA ME");
                messageBuilder.insertFontTag(message.defaultFontSize, "FFF", message.defaultFontFace, "XI");
                messageBuilder.insertFontTag(message.defaultFontSize, "0B0", message.defaultFontFace, "CO");
            } else {
                for (int i = 0; i <= 10; i++) {
                    messageBuilder.insertFontTag(message.defaultFontSize, "F00", message.defaultFontFace, "U");
                    messageBuilder.insertFontTag(message.defaultFontSize, "FFF", message.defaultFontFace, "S");
                    messageBuilder.insertFontTag(message.defaultFontSize, "00F", message.defaultFontFace, "A ");

                }
            }
            messageSender.send(messageBuilder.toString());
            messageBuilder.message.clear();

        }

        @Override
        public boolean conditions(Message message) {
            if (message.getTextAsString().contains("USA")) {
                Logger.debug("Got USA command");
                return true;
            }
            return false;
        }
    },
    AIRHORN {
        @Override
        public void doAction(Message message) {

            messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "https://www.youtube.com/watch?v=-i6rFjNQUwE");
            messageSender.send(messageBuilder.toString());
            messageBuilder.message.clear();

        }

        @Override
        public boolean conditions(Message message) {
            if (message.getTextAsString().toLowerCase().contains("airhorn party")) {
                Logger.debug("Got airhorn command");
                return true;
            }
            return false;
        }
    },
    MODLIST {
        @Override
        public void doAction(Message message) {


            messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "Mods: ");

            for (String s : roomInfo.modList) {
                messageBuilder.insertFontTag("", "", "", s + " ");
            }
            messageSender.send(messageBuilder.toString());

            messageBuilder.message.clear();

        }

        @Override
        public boolean conditions(Message message) {
            if (!roomInfo.modList.contains(message.getSender().toLowerCase())) {
                return false;
            }

            if (message.getTextAsString().toLowerCase().startsWith("/modlist")) {
                Logger.debug("Got modlist command");
                return true;

            }

            return false;
        }
    },
    BOTRESPONSE {
        @Override
        public void doAction(Message message) {
            Random random = new Random();
            int roll = random.nextInt(50) + 1;

            if (roll == 3) {
                messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "Shut up " + message.getSender());
                messageSender.send(messageBuilder.toString());
                messageBuilder.message.clear();
            }

        }

        @Override
        public boolean conditions(Message message) {
            if (message.getSender().toLowerCase().equals("jigobot") || message.getSender().toLowerCase().equals("kilbroy")) {
                return true;
            }
            return false;
        }
    },
    HONK {
        @Override
        public void doAction(Message message) {
            messageBuilder.insertFontTag(message.defaultFontSize, message.defaultFontColor, message.defaultFontFace, "Honk");
            messageSender.send((messageBuilder.toString()));
            messageBuilder.message.clear();


        }

        @Override
        public boolean conditions(Message message) {
            if ((random.nextInt(100) + 1) == 4) {
                return true;
            }

            return false;
        }
    };

    public RoomInfo roomInfo = RoomInfo.getInstance();
    public static MessageSender messageSender = MessageSender.getInstance();
    public MessageBuilder messageBuilder = new MessageBuilder();
    private static Random random = new Random();

}