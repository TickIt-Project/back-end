package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class CompanyRoleNotFoundException extends RuntimeException {
    public CompanyRoleNotFoundException(String message) {
        super("The company role id " + message + " does not exist");
    }
}
