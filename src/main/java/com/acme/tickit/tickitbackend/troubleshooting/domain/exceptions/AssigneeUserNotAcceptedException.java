package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

public class AssigneeUserNotAcceptedException extends RuntimeException {
    public AssigneeUserNotAcceptedException() {
        super("The assignee user has not been accepted for this update");
    }
}
