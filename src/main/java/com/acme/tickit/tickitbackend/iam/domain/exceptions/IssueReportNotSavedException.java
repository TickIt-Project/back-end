package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class IssueReportNotSavedException extends RuntimeException {
    public IssueReportNotSavedException(String message) {
        super("The issue report could not be saved: " + message);
    }
}
