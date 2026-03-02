package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyRoleNotFoundException extends DomainException {
    public CompanyRoleNotFoundException(String message) {
        super("The company role id " + message + " does not exist", HttpStatus.NOT_FOUND);
    }
}
