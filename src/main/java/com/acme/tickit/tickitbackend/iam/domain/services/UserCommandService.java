package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;

import java.util.UUID;

public interface UserCommandService {
    UUID handle(CreateUserCommand command);
}
