package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CompanyRoleNotCreatedException extends RuntimeException {
    public CompanyRoleNotCreatedException(String message) {
        super("This company role could not be created: " + message);
    }
}
