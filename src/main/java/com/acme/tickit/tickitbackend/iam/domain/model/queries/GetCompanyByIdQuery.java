package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.CompanyIdNullException;

import java.util.UUID;

public record GetCompanyByIdQuery(UUID companyId) {
    public GetCompanyByIdQuery {
        if (companyId == null) {
            throw new CompanyIdNullException();
        }
    }
}
