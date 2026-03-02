package com.acme.tickit.tickitbackend.management.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ItMemberIdNotAcceptedException extends DomainException {
    public ItMemberIdNotAcceptedException() {
        super("IT member id null not accepted", HttpStatus.NOT_ACCEPTABLE);
    }
}
