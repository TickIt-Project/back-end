package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record GetAllUsersQuery(UUID tenantId) {
    public GetAllUsersQuery {
        if (tenantId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
