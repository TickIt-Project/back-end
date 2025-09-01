package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameNotAcceptedException;

public record UpdateUserPasswordResource(String username, String oldPassword, String newPassword) {
    public UpdateUserPasswordResource {
        if (username == null || username.isEmpty()) {
            throw new UserNameNotAcceptedException();
        }
        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
        if (newPassword == null || newPassword.length() < 8) {
            throw new PasswordNotAcceptedException();
        }
    }
}
