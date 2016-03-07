package io.smsgw.core;

import java.util.Calendar;

public class Log {

    private static boolean coloredShell = true;
    public static boolean debug = true;

    private enum Color {
        GREEN("\u001B[32m"),
        RED("\u001B[31m"),
        WHITE("\u001B[37m"),
        YELLOW("\u001B[33m"),
        CYAN("\u001B[36m"),
        RESET("\u001B[0m"),
        BLACK("\u001B[30m"),
        BLUE("\u001B[34m"),
        PURPLE("\u001B[35m");

        public String value;
        Color(String value){this.value = value;}
    }

    public static void d(String tag, String message) {
        if (debug) {
            log(tag, message, Color.WHITE);
        }
    }

    public static void v(String tag, String message) {
        if (debug) {
            log(tag, message, Color.CYAN);
        }
    }

    public static void w(String tag, String message) {
        if (debug) {
            log(tag, message, Color.YELLOW);
        }
    }

    public static void e(String tag, String message) {
        if (debug) {
            log(tag, message, Color.RED);
        }
    }

    public static void c(String tag, String message) {
        log(tag, message, Color.WHITE);
    }

    private static void log(String tag, String message, Color color) {

        String timestamp = Calendar.getInstance().getTime().toString();

        System.out.println(String.format("%s%s %s: %s %s", color.value, timestamp, tag, message, Color.WHITE.value));
    }

    public static void setColored() {
        coloredShell = !coloredShell;
    }

}
