package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class UserIdNotAcceptedException extends RuntimeException {
    public UserIdNotAcceptedException() {
        super("The user ID cannot be null");
    }
}
