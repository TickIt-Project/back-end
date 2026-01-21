package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ScreenUrlNotAcceptedException extends RuntimeException {
    public ScreenUrlNotAcceptedException() {
        super("The url of the screen location is not accepted");
    }
}
