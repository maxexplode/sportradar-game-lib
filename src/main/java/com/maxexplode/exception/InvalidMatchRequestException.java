package com.maxexplode.exception;

public class InvalidMatchRequestException extends ScoreBoardException {
    public InvalidMatchRequestException(String message) {
        super(message);
    }

    public InvalidMatchRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
