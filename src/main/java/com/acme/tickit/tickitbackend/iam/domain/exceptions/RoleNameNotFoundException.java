package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class RoleNameNotFoundException extends DomainException {
    public RoleNameNotFoundException(String message) {
        super("The role named " + message + " was not found", HttpStatus.NOT_FOUND);
    }
}
