package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportReporterIdNotAcceptedException extends DomainException {
    public IssueReportReporterIdNotAcceptedException() {
        super("The reporter ID cannot be empty", HttpStatus.NOT_ACCEPTABLE);
    }
}
