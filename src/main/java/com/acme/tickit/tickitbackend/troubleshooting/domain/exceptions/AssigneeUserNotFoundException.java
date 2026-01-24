package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class AssigneeUserNotFoundException extends RuntimeException {
    public AssigneeUserNotFoundException(String message) {
        super("There is no user with this id: " + message);
    }
}
