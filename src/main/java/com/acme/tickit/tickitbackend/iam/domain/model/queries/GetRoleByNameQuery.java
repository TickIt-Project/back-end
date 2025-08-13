package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.RoleNameNullException;
import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;

public record GetRoleByNameQuery(Roles name) {
    public GetRoleByNameQuery {
        if (name == null) {
            throw new RoleNameNullException();
        }
    }
}
