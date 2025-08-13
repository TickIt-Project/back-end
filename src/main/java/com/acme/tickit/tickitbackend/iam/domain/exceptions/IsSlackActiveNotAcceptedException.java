package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class IsSlackActiveNotAcceptedException extends RuntimeException {
    public IsSlackActiveNotAcceptedException() {
        super("Is slack active value cannot be null");
    }
}
