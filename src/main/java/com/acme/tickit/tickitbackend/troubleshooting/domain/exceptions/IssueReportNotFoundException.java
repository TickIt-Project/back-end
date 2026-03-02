package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportNotFoundException extends DomainException {
    public IssueReportNotFoundException(String message) {
        super("The issue report with id " + message + " was not found", HttpStatus.NOT_FOUND);
    }
}
