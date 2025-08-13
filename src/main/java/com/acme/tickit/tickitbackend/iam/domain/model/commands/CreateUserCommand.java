package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.*;

public record CreateUserCommand(String username, String email,
                                String role, String password,
                                Boolean notify_active, String companyCode) {
    public CreateUserCommand {
        if (username == null || username.length() < 2) {
            throw new UserNameNotAcceptedException();
        }
        if (email == null || !email.matches(".+@.+\\..+")) {
            throw new UserEmailNotAcceptedException();
        }
        if (role != "IT_HEAD" && role != "IT_MEMBER" && role != "EMPLOYEE") {
            throw new RoleNotAcceptedException();
        }
        if (role != "IT_MEMBER" && (password == null || password.length() < 8)) {
            throw new PasswordNotAcceptedException();
        }
        if (notify_active == null) {
            throw new NotifyActiveNullException();
        }
        if (companyCode == null || companyCode.length() != 6) {
            throw new CompanyCodeNotAcceptedException();
        }
    }
}
