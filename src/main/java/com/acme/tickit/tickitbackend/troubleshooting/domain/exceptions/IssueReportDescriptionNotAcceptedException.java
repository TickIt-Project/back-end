package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportDescriptionNotAcceptedException extends DomainException {
    public IssueReportDescriptionNotAcceptedException() {
        super("The description cannot be empty or has less than 30 characters", HttpStatus.NOT_ACCEPTABLE);
    }
}
