package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportDescriptionNotAcceptedException extends RuntimeException {
    public IssueReportDescriptionNotAcceptedException() {
        super("The description cannot be empty or has less than 30 characters");
    }
}
