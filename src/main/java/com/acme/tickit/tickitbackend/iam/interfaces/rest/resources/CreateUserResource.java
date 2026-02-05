package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.*;

import java.util.Objects;
import java.util.UUID;

public record CreateUserResource(String username, String email,
                                 String role, String password,
                                 Boolean notify_active, String companyCode,
                                 UUID companyRoleId, String language) {
    public CreateUserResource {
        if (username == null || username.length() < 2) {
            throw new UserNameNotAcceptedException();
        }
        if (email == null || !email.matches(".+@.+\\..+")) {
            throw new UserEmailNotAcceptedException();
        }
        if (!Objects.equals(role, "IT_HEAD") && !Objects.equals(role, "IT_MEMBER") && !Objects.equals(role, "EMPLOYEE")) {
            throw new RoleNotAcceptedException();
        }
        if (!role.equals("IT_MEMBER") && (password == null || password.length() < 8)) {
            throw new PasswordNotAcceptedException();
        }
        if (notify_active == null) {
            throw new NotifyActiveNullException();
        }
        if (companyCode == null || companyCode.length() != 7) {
            throw new CompanyCodeNotAcceptedException();
        }
    }
}
