package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class IssueReportTitleNotAcceptedException extends RuntimeException {
    public IssueReportTitleNotAcceptedException() {
        super("The title cannot be empty or null.");
    }
}
