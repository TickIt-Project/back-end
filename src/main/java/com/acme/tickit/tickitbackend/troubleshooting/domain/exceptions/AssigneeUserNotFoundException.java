package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class AssigneeUserNotFoundException extends DomainException {
    public AssigneeUserNotFoundException(String message) {
        super("There is no user with this id: " + message, HttpStatus.NOT_FOUND);
    }
}
