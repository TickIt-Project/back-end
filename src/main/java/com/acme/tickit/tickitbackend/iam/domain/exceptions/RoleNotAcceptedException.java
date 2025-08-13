package com.acme.tickit.tickitbackend.iam.domain.exceptions;

public class RoleNotAcceptedException extends RuntimeException {
    public RoleNotAcceptedException() {
        super("The role is not accepted, it must be IT_HEAD, IT_MEMBER or EMPLOYEE");
    }
}
