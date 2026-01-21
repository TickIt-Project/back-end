package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record GetAllCompanyRolesQuery(UUID companyId) {
    public GetAllCompanyRolesQuery {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
