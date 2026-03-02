package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class RoleNotAcceptedException extends DomainException {
    public RoleNotAcceptedException() {
        super("The role is not accepted, it must be IT_HEAD, IT_MEMBER or EMPLOYEE", HttpStatus.NOT_ACCEPTABLE);
    }
}
