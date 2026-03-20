package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueCoincidenceIdNotAcceptedException extends DomainException {
    public IssueCoincidenceIdNotAcceptedException() {
        super("The issue coincidence id is not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
