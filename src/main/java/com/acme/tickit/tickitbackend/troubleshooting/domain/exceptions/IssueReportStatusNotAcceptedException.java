package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportStatusNotAcceptedException extends RuntimeException {
    public IssueReportStatusNotAcceptedException() {
        super("The new status for the issue report cannot be null");
    }
}
