package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportReporterIdNotAcceptedException extends RuntimeException {
    public IssueReportReporterIdNotAcceptedException() {
        super("The reporter ID cannot be empty");
    }
}
