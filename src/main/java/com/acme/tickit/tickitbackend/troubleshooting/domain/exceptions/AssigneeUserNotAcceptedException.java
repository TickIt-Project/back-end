package com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.DomainException;
import org.springframework.http.HttpStatus;

public class AssigneeUserNotAcceptedException extends DomainException {
    public AssigneeUserNotAcceptedException() {
        super("The assignee user has not been accepted for this update", HttpStatus.NOT_ACCEPTABLE);
    }
}
