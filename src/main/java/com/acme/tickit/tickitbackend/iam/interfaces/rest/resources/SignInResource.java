package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.EmailNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;

public record SignInResource(String email, String password) {
    public SignInResource {
        if (email == null || email.isEmpty()) {
            throw new EmailNotAcceptedException();
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
    }
}
