package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.PasswordNotAcceptedException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserIdNullException;
import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record UpdateUserPasswordCommand(UUID tenantId, UUID userId, String oldPassword, String newPassword) {
    public UpdateUserPasswordCommand {
        if (tenantId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (userId == null) {
            throw new UserIdNullException();
        }
        if (oldPassword == null || oldPassword.isEmpty()) {
            throw new PasswordNotAcceptedException();
        }
        if (newPassword == null || newPassword.length() < 8) {
            throw new PasswordNotAcceptedException();
        }
    }
}
