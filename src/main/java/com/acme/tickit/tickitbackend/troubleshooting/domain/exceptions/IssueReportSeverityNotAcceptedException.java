package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportSeverityNotAcceptedException extends DomainException {
    public IssueReportSeverityNotAcceptedException() {
        super("Severity cannot be empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
