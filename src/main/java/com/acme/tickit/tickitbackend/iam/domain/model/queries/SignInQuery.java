package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameNotAcceptedException;

public record SignInQuery(String username, String password) {
    public SignInQuery {
        if (username == null || username.isEmpty()) {
            throw new UserNameNotAcceptedException();
        }
        if (password == null || password.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
    }
}
