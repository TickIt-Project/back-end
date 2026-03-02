package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class IssueReportTitleNotAcceptedException extends DomainException {
    public IssueReportTitleNotAcceptedException() {
        super("The title cannot be empty or null.", HttpStatus.NOT_ACCEPTABLE);
    }
}
