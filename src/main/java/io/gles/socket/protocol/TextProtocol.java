package io.gles.socket.protocol;

import java.util.List;
import java.util.regex.Pattern;

import static io.gles.socket.protocol.TextConstants.*;
import static io.gles.socket.protocol.TextConstants.LINE_SEPARATOR;

public class TextProtocol {

    public String query(final String message) {
        return format(message);
    }

    public String payload(final List payloads) {
        return format(String.join(LINE_SEPARATOR, payloads).concat(LINE_SEPARATOR));
    }

    public String info(String message) {
        return format("INFO=".concat(message));
    }

    public String error(String message) {
        return format("ERROR=".concat(message));
    }

    private String format(final String message) {
        return String.format("%d:%s", message.length(), message);
    }

    public static boolean isQuery(final String message) {
        return Pattern.matches(PATTERN, message);
    }

}
