package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class RoleNameNotAcceptedException extends RuntimeException {
    public RoleNameNotAcceptedException() {
        super("The role name is not accepted");
    }
}
