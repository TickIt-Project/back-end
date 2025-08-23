package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameNotAcceptedException;

public record SignInCommand(String username, String password) {
    public SignInCommand {
        if (username == null || username.isEmpty()) {
            throw new UserNameNotAcceptedException();
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
    }
}
