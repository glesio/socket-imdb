package io.gles.imdb;

public class IMDBException extends Exception {

    private static final long serialVersionUID = 1L;

    public IMDBException(String message, Throwable cause) {
        super(message, cause);
    }
}
