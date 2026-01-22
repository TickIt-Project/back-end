package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportSeverityNotAcceptedException extends RuntimeException {
    public IssueReportSeverityNotAcceptedException() {
        super("Severity cannot be empty");
    }
}
