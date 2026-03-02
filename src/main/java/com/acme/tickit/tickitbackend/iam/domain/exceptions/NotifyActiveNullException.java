package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class NotifyActiveNullException extends DomainException {
    public NotifyActiveNullException() {
        super("Notify active cannot be null", HttpStatus.BAD_REQUEST);
    }
}
