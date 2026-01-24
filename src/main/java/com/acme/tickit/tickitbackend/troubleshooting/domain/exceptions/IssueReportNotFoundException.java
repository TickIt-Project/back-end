package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportNotFoundException extends RuntimeException {
    public IssueReportNotFoundException(String message) {
        super("The issue report with id " + message + " was not found");
    }
}
