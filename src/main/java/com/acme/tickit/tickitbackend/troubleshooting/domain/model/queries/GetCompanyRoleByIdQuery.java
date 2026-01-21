package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CompanyRoleIdNotAcceptedException;

import java.util.UUID;

public record GetCompanyRoleByIdQuery(UUID companyRoleId) {
    public GetCompanyRoleByIdQuery {
        if (companyRoleId == null) {
            throw new CompanyRoleIdNotAcceptedException();
        }
    }
}
