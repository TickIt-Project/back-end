package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportIdNotAcceptedException extends RuntimeException {
    public IssueReportIdNotAcceptedException() {
        super("The issue report cannot be null");
    }
}
