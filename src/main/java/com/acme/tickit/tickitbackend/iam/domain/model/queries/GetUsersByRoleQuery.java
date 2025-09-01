package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.RoleNotAcceptedException;
import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.Objects;
import java.util.UUID;

public record GetUsersByRoleQuery(UUID tenantId, String role) {
    public GetUsersByRoleQuery {
        if (tenantId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (role == null || role.isEmpty() || (!Objects.equals(role, "IT_HEAD") && !Objects.equals(role, "IT_MEMBER") && !Objects.equals(role, "EMPLOYEE"))) {
            throw new RoleNotAcceptedException();
        }
    }
}
