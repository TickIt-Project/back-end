package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class JiraEmailNullException extends DomainException {
    public JiraEmailNullException() {
        super("Jira email cannot be null", HttpStatus.BAD_REQUEST);
    }
}
