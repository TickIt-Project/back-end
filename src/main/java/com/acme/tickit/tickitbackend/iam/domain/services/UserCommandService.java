package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.SignInCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.UpdateUserPasswordCommand;

import java.util.Optional;
import java.util.UUID;

public interface UserCommandService {
    UUID handle(CreateUserCommand command);
    UUID handle(SignInCommand query);
    Optional<User> handle(UpdateUserPasswordCommand command);
}
