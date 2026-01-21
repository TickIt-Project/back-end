package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ScreenLocationIdNotAcceptedException extends RuntimeException {
    public ScreenLocationIdNotAcceptedException() {
        super("The screen location id is not accepted or does not exists");
    }
}
