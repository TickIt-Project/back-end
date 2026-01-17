package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record GetAllScreenLocationsQuery(UUID companyId) {
    public GetAllScreenLocationsQuery {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
