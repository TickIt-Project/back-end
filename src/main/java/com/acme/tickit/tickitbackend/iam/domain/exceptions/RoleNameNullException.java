package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class RoleNameNullException extends DomainException {
    public RoleNameNullException() {
        super("Role name cannot be null", HttpStatus.BAD_REQUEST);
    }
}
