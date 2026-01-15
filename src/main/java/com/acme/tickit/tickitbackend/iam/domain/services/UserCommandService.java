package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.SignInCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.UpdateUserPasswordCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Optional;
import java.util.UUID;

public interface UserCommandService {
    UUID handle(CreateUserCommand command);
    Optional<ImmutablePair<User, String>> handle(SignInCommand query);
    Optional<User> handle(UpdateUserPasswordCommand command);
}
