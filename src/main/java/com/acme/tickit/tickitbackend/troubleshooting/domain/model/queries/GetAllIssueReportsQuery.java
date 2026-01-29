package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record GetAllIssueReportsQuery(UUID companyId) {
    public GetAllIssueReportsQuery {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
