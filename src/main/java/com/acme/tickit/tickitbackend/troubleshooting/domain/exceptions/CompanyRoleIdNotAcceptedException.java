package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class CompanyRoleIdNotAcceptedException extends DomainException {
    public CompanyRoleIdNotAcceptedException() {
        super("The company role id is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
