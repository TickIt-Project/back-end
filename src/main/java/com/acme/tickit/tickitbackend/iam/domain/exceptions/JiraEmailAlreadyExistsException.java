package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class JiraEmailAlreadyExistsException extends DomainException {
    public JiraEmailAlreadyExistsException(String message) {
        super("Jira email " + message + "already exists", HttpStatus.CONFLICT);
    }
}
