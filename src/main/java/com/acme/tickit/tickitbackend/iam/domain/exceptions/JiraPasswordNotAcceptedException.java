package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class JiraPasswordNotAcceptedException extends DomainException {
    public JiraPasswordNotAcceptedException() {
        super("Jira password cannot be null or empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
