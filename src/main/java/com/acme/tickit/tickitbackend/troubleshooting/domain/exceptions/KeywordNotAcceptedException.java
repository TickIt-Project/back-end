package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class KeywordNotAcceptedException extends RuntimeException {
    public KeywordNotAcceptedException(String message) {
        super("The word " + message + " is not accepted");
    }
}
