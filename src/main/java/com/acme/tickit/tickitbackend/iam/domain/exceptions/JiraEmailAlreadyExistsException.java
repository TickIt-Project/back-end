package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class JiraEmailAlreadyExistsException extends RuntimeException {
    public JiraEmailAlreadyExistsException(String message) {
        super("Jira email " + message + "already exists");
    }
}
