package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class ItHeadRepeatedException extends RuntimeException {
    public ItHeadRepeatedException() {
        super("There can only be one IT Head per company");
    }
}
