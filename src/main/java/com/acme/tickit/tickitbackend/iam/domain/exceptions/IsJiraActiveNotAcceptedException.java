package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class IsJiraActiveNotAcceptedException extends RuntimeException {
    public IsJiraActiveNotAcceptedException() {
        super("Is jira active value cannot be null");
    }
}
