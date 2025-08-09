package com.acme.tickit.tickitbackend.iam.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;

public record Password(String password) {
    public Password {
        if (password == null || password.length() < 8) {
            throw new PasswordNotAcceptedException();
        }
    }
}
