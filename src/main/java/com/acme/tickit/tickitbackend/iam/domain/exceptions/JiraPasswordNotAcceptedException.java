package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class JiraPasswordNotAcceptedException extends RuntimeException {
    public JiraPasswordNotAcceptedException() {
        super("Jira password cannot be null or empty");
    }
}
