package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class JiraEmailNullException extends RuntimeException {
    public JiraEmailNullException() {
        super("Jira email cannot be null");
    }
}
