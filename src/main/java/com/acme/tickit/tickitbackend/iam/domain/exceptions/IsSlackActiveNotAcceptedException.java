package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IsSlackActiveNotAcceptedException extends DomainException {
    public IsSlackActiveNotAcceptedException() {
        super("Is slack active value cannot be null", HttpStatus.NOT_ACCEPTABLE);
    }
}
