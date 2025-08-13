package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class NotifyActiveNullException extends RuntimeException {
    public NotifyActiveNullException() {
        super("Notify active cannot be null");
    }
}
