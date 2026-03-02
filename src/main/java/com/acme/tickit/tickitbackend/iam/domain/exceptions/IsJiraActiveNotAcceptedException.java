package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IsJiraActiveNotAcceptedException extends DomainException {
    public IsJiraActiveNotAcceptedException() {
        super("Is jira active value cannot be null", HttpStatus.NOT_ACCEPTABLE);
    }
}
