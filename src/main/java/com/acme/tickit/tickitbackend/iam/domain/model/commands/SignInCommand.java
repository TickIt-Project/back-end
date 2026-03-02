package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.EmailNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;

public record SignInCommand(String email, String password) {
    public SignInCommand {
        if (email == null || email.isEmpty()) {
            throw new EmailNotAcceptedException();
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
    }
}
