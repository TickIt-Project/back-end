package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;

import java.util.Set;

public record GetUsersWithRolesInQuery(Set<Roles> roles) {
    public GetUsersWithRolesInQuery {
        if (roles == null || roles.isEmpty()) {
            throw new IllegalArgumentException("roles cannot be null or empty");
        }
    }
}
