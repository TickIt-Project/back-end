package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameNotAcceptedException;

public record SignInResource(String username, String password) {
    public SignInResource {
        if (username == null || username.isEmpty()) {
            throw new UserNameNotAcceptedException();
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
    }
}
