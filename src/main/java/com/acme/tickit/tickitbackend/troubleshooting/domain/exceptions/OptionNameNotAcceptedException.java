package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class OptionNameNotAcceptedException extends RuntimeException {
    public OptionNameNotAcceptedException() {
        super("The name for the option cannot be null or empty");
    }
}
