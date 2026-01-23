package com.acme.tickit.tickitbackend.iam.interfaces.acl;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;

import java.util.Optional;
import java.util.UUID;

public interface UserContextFacade {
    Boolean ExistsUserById(UUID userId);
    Optional<User> GetUserById(UUID userId);
}
