package com.acme.tickit.tickitbackend.iam.application.internal.queryservices;

import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.HashingService;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.InvalidPasswordException;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNotFoundException;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.SignInQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.acme.tickit.tickitbackend.shared.infrastructure.multitenancy.TenantContext;
import com.acme.tickit.tickitbackend.shared.infrastructure.security.JwtService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserQueryServiceImpl implements UserQueryService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final HashingService hashingService;

    public UserQueryServiceImpl(UserRepository userRepository, JwtService jwtService, HashingService hashingService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.hashingService = hashingService;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        UUID companyId = TenantContext.getCurrentTenantId();
        return userRepository.findAllByCompany_Id(companyId);
    }

    @Override
    public String handle(SignInQuery query) {
        User user = userRepository.findByPersonalData_Name(query.username())
                .orElseThrow(() -> new UserNotFoundException(query.username()));
        if (!hashingService.matches(query.password(), user.getPassword().password()))
            throw new InvalidPasswordException();
        TenantContext.setCurrentTenantId(user.getCompany().getId());
        return jwtService.generateToken(
                user.getId().toString(),
                user.getPersonalData().name(),
                user.getRole().getStringName(),
                user.getCompany().getId().toString()
        );
    }
}
