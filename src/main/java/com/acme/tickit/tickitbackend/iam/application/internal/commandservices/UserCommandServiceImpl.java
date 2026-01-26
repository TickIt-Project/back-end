package com.acme.tickit.tickitbackend.iam.application.internal.commandservices;

import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.HashingService;
import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.TokenService;
import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.acl.ExternalCompanyRoleService;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.*;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.DeleteUserByIdCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.SignInCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.UpdateUserPasswordCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.CompanyCode;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.CompanyRepository;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCommandServiceImpl implements UserCommandService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final TokenService tokenService;
    private final ExternalCompanyRoleService externalCompanyRoleService;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  HashingService hashingService,
                                  CompanyRepository companyRepository,
                                  RoleRepository roleRepository,
                                  TokenService tokenService,
                                  ExternalCompanyRoleService externalCompanyRoleService) {
        this.userRepository = userRepository;
        this.hashingService = hashingService;
        this.companyRepository = companyRepository;
        this.roleRepository = roleRepository;
        this.tokenService = tokenService;
        this.externalCompanyRoleService = externalCompanyRoleService;
    }

    @Override
    public UUID handle(CreateUserCommand command) {
        if (userRepository.existsByPersonalData_Name(command.username()))
            throw new UserNameAlreadyExistsException(command.username());
        if (userRepository.existsByPersonalData_Email(command.email()))
            throw new EmailAlreadyExistsException(command.email());
        if (!companyRepository.existsByCode(new CompanyCode(command.companyCode())))
            throw new CompanyCodeNotFoundException(command.companyCode());
        if (!roleRepository.existsByName(Roles.valueOf(command.role())))
            throw new RoleNameNotFoundException(command.role());
        if (Objects.equals(command.role(), "IT_HEAD") &&
            userRepository.existsByCompany_CodeAndRole_Name(new CompanyCode(command.companyCode()), Roles.valueOf(command.role())))
            throw new ItHeadRepeatedException();
        if (command.companyRoleId() != null && !externalCompanyRoleService.ExistsCompanyRoleById(command.companyRoleId()))
            throw new CompanyRoleNotFoundException(command.companyRoleId().toString());
        var company = companyRepository.findByCode(new CompanyCode(command.companyCode())).get();
        var role = roleRepository.findByName(Roles.valueOf(command.role())).get();
        var finalPassword = command.password();
        if (finalPassword == null) {
            SecureRandom r = new SecureRandom();
            finalPassword = r.ints(48, 123)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(8)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
        }
        var user = new User(command, hashingService.encode(finalPassword), company, role);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotCreatedException(e.getMessage());
        }
        return user.getId();
    }

    @Override
    public Optional<ImmutablePair<User, String>> handle(SignInCommand query) {
        User user = userRepository.findByPersonalData_Name(query.username())
                .orElseThrow(() -> new UserNotFoundException(query.username()));
        if (!hashingService.matches(query.password(), user.getPassword().password()))
            throw new InvalidPasswordException();
        var token = tokenService.generateToken(user.getPersonalData().email());
        return Optional.of(ImmutablePair.of(user, token));
    }

    @Override
    public Optional<User> handle(UpdateUserPasswordCommand command) {
        User user = userRepository.findByCompany_IdAndId(command.tenantId(), command.userId())
                .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));
        if (!hashingService.matches(command.oldPassword(), user.getPassword().password()))
            throw new InvalidPasswordException();
        try {
            user.updatePassword(hashingService.encode(command.newPassword()));
            userRepository.save(user);
        } catch (Exception e) {
            throw new UserNotSavedException(e.getMessage());
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> handle(DeleteUserByIdCommand command) {
        User user = userRepository.findById(command.id())
                .orElseThrow(() -> new UserNotFoundException(command.id().toString()));
        userRepository.delete(user);
        return Optional.of(user);
    }
}
