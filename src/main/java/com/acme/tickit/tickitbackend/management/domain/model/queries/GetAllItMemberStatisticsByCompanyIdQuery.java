package com.acme.tickit.tickitbackend.management.domain.model.queries;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record GetAllItMemberStatisticsByCompanyIdQuery(UUID companyId) {
    public GetAllItMemberStatisticsByCompanyIdQuery {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
