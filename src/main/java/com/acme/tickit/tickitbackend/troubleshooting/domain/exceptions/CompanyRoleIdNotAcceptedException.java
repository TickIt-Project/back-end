package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class CompanyRoleIdNotAcceptedException extends RuntimeException {
    public CompanyRoleIdNotAcceptedException() {
        super("The company role id is not accepted");
    }
}
