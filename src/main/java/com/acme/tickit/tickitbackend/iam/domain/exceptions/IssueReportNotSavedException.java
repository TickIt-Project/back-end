package com.acme.tickit.tickitbackend.iam.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportNotSavedException extends DomainException {
    public IssueReportNotSavedException(String message) {
        super("The issue report could not be saved: " + message, HttpStatus.BAD_REQUEST);
    }
}
