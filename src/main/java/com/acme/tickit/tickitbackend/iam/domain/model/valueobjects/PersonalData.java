package com.acme.tickit.tickitbackend.iam.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.EmailNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.NameNotAcceptedException;

public record PersonalData(String name, String email) {
    public PersonalData {
        if (name == null || name.isEmpty()) {
            throw new NameNotAcceptedException();
        }
        if (email == null || email.isEmpty() || !email.matches(".+@.+\\..+")) {
            throw new EmailNotAcceptedException();
        }
    }
}
