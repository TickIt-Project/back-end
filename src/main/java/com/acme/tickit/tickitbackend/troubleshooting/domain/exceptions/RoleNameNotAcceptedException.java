package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class RoleNameNotAcceptedException extends DomainException {
    public RoleNameNotAcceptedException() {
        super("The role name is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
