package org.example.javatest.exception;

public class GameServiceException extends RuntimeException {
    public GameServiceException(String message, Throwable e) {
        super(message, e);
    }
}
