package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class RoleNameNotFoundException extends RuntimeException {
    public RoleNameNotFoundException(String message) {
        super("The role named " + message + " was not found");
    }
}
