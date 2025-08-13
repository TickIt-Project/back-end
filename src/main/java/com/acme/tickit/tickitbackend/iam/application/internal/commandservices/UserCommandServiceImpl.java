package com.acme.tickit.tickitbackend.iam.application.internal.commandservices;

import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.HashingService;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameAlreadyExistsException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNotCreatedException;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.PersonalData;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(UserRepository userRepository, HashingService hashingService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
    }

    @Override
    public UUID handle(CreateUserCommand command) {
        if (userRepository.existsByPersonalData(new PersonalData(command.username(), command.email())))
            throw new UserNameAlreadyExistsException(command.username());
        /**
         * missing some validations
         */
        var user = new User(command, hashingService.encode(command.password()));
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotCreatedException(e.getMessage());
        }
        return user.getId();
    }
}
