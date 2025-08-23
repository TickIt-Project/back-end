package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.SignInCommand;

import java.util.UUID;

public interface UserCommandService {
    UUID handle(CreateUserCommand command);
    UUID handle(SignInCommand query);
}
