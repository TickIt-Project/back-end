package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.UserIdNotAcceptedException;

import java.util.UUID;

public record DeleteUserByIdCommand(UUID id) {
    public DeleteUserByIdCommand {
        if (id == null) {
            throw new UserIdNotAcceptedException();
        }
    }
}
