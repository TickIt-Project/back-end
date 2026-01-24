package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportNotCreatedException extends RuntimeException {
    public IssueReportNotCreatedException(String message) {
        super("The issue report could not be created: " + message);
    }
}
