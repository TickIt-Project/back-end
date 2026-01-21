package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class ScreenLocationNameNotAcceptedException extends RuntimeException {
    public ScreenLocationNameNotAcceptedException() {
        super("The name of the screen location is not accepted");
    }
}
