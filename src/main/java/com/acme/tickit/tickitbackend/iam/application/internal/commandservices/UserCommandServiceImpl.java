package com.acme.tickit.tickitbackend.iam.application.internal.commandservices;

import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.HashingService;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.CompanyCodeNotFoundException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.RoleNameNotFoundException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNameAlreadyExistsException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNotCreatedException;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.entities.Role;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.PersonalData;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.CompanyRepository;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService,
                                  CompanyRepository companyRepository,
                                  RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UUID handle(CreateUserCommand command) {
        if (userRepository.existsByPersonalData(new PersonalData(command.username(), command.email())))
            throw new UserNameAlreadyExistsException(command.username());
        if (!companyRepository.existsByCode(new CompanyCode(command.companyCode())))
            throw new CompanyCodeNotFoundException(command.companyCode());
        if (!roleRepository.existsByName(Roles.valueOf(command.role())))
            throw new RoleNameNotFoundException(command.role());
        var company = companyRepository.findByCode(new CompanyCode(command.companyCode())).get();
        var role = roleRepository.findByName(Roles.valueOf(command.role())).get();
        var user = new User(command, hashingService.encode(command.password()), company, role);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotCreatedException(e.getMessage());
        }
        return user.getId();
    }
}
