package io.gles.socket.protocol;

public class TextConstants {

    private TextConstants() {
        throw new IllegalStateException("Constants class");
    }

    public final static String QUERY_SEPARATOR = ":";
    public final static String PATTERN = "\\d+\\:(\\w|\\W)+";
    public final static String LINE_SEPARATOR = "\n";
    public final static String CLOSE_COMMAND = ":close";
}
