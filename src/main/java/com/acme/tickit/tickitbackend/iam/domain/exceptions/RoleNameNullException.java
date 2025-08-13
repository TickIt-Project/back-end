package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class RoleNameNullException extends RuntimeException {
    public RoleNameNullException() {
        super("Role name cannot be null");
    }
}
