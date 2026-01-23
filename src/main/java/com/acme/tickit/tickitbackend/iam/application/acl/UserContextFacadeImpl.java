package com.acme.tickit.tickitbackend.iam.application.acl;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.acl.UserContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserContextFacadeImpl implements UserContextFacade {
    private final UserQueryService userQueryService;

    public UserContextFacadeImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Boolean ExistsUserById(UUID userId) {
        var getUser = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUser);
        return user.isPresent();
    }

    @Override
    public Optional<User> GetUserById(UUID userId) {
        var getUser = new GetUserByIdQuery(userId);
        return userQueryService.handle(getUser);
    }
}
