package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportIdNotAcceptedException extends DomainException {
    public IssueReportIdNotAcceptedException() {
        super("The issue report cannot be null", HttpStatus.NOT_ACCEPTABLE);
    }
}
