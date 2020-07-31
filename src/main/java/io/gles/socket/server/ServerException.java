package io.gles.socket.server;

public class ServerException extends Exception {

    private static final long serialVersionUID = 1L;

    public ServerException(String message) {
        super(message);
    }

    public ServerException(String message, Throwable cause) {
        super(message, cause);
    }

}
