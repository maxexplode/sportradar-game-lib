package com.maxexplode.exception;

public class InvalidMatchStateException extends ScoreBoardException {
    public InvalidMatchStateException(String message) {
        super(message);
    }

    public InvalidMatchStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
