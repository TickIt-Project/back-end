package com.acme.tickit.tickitbackend.management.application.internal.outboundservices.acl;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;
import com.acme.tickit.tickitbackend.iam.interfaces.acl.UserContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ExternalUserService {

    private final UserContextFacade userContextFacade;

    public ExternalUserService(UserContextFacade userContextFacade) {
        this.userContextFacade = userContextFacade;
    }

    public Optional<User> getUserById(UUID userId) {
        return userContextFacade.GetUserById(userId);
    }

    public List<User> getAllItMembers() {
        return userContextFacade.getAllUsersWithRolesIn(Set.of(Roles.IT_HEAD, Roles.IT_MEMBER));
    }
}
