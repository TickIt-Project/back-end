package com.acme.tickit.tickitbackend.iam.interfaces.acl;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface UserContextFacade {
    Boolean ExistsUserById(UUID userId);
    Optional<User> GetUserById(UUID userId);
    List<User> getAllUsersWithRolesIn(Set<Roles> roles);
}
