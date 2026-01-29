package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserIdNullException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameNotAcceptedException;

import java.util.UUID;

public record UpdateUserPasswordResource(UUID userId, String oldPassword, String newPassword) {
    public UpdateUserPasswordResource {
        if (userId == null) {
            throw new UserIdNullException();
        }
        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
        if (newPassword == null || newPassword.length() < 8) {
            throw new PasswordNotAcceptedException();
        }
    }
}
