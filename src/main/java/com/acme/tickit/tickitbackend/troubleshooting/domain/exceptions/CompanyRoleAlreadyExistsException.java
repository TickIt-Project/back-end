package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CompanyRoleAlreadyExistsException extends RuntimeException {
    public CompanyRoleAlreadyExistsException(String message) {
        super("The company role " + message + " already exists");
    }
}
