package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportNotCreatedException extends DomainException {
    public IssueReportNotCreatedException(String message) {
        super("The issue report could not be created: " + message, HttpStatus.BAD_REQUEST);
    }
}
