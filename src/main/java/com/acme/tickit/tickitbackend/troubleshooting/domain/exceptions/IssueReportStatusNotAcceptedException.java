package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportStatusNotAcceptedException extends DomainException {
    public IssueReportStatusNotAcceptedException() {
        super("The new status for the issue report cannot be null", HttpStatus.NOT_ACCEPTABLE);
    }
}
