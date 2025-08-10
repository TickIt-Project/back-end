package com.acme.tickit.tickitbackend.management.domain.exceptions;

public class ItMemberIdNotAcceptedException extends RuntimeException {
    public ItMemberIdNotAcceptedException() {
        super("IT member id null not accepted");
    }
}
