package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class JiraEmailNotAcceptedException extends RuntimeException {
    public JiraEmailNotAcceptedException() {
        super("Jira email should have the correct format");
    }
}
