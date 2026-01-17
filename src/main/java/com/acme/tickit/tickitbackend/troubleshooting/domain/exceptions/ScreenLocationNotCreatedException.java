package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ScreenLocationNotCreatedException extends RuntimeException {
    public ScreenLocationNotCreatedException(String message) {
        super("This screen location could not be created " + message);
    }
}
