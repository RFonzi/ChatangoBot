package io.github.rfonzi.chatangobot.logging;

import java.io.OutputStream;
import java.io.PrintStream;

public class Logger {

    public void error(String text){
        log(Level.ERROR, text);
    }

    public static void warn(String text){
        log(Level.WARN, text);
    }

    public static void info(String text){
        log(Level.INFO, text);
    }

    public static void debug(String text){
        log(Level.DEBUG, text);
    }

    public static void trace(String text){
        log(Level.TRACE, text);
    }

    public static void setOutputStream(PrintStream out){
        System.setOut(out);
    }

    private static void log(int level, String text){
        StringBuilder sb = new StringBuilder(128);

        if(level == Level.ERROR) sb.append("ERROR: ");
        if(level == Level.WARN) sb.append("WARN:  ");
        if(level == Level.INFO) sb.append("INFO:  ");
        if(level == Level.DEBUG) sb.append("DEBUG: ");
        if(level == Level.TRACE) sb.append("TRACE: ");

        sb.append(text);

        for(int i = 0; i < sb.length(); i++){
            if(sb.charAt(i) == '\n'){
                sb.replace(i, i + 1, "\\n");
            }
            else if(sb.charAt(i) == '\r'){
                sb.replace(i, i + 1, "\\r");
            }

        }

        System.out.println(sb.toString());

    }

}
