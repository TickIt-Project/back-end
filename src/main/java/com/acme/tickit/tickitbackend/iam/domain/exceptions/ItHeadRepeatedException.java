package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class ItHeadRepeatedException extends DomainException {
    public ItHeadRepeatedException() {
        super("There can only be one IT Head per company", HttpStatus.CONFLICT);
    }
}
