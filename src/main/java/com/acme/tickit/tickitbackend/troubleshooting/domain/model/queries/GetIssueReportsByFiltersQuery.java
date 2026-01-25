package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;

import java.util.UUID;

public record GetIssueReportsByFiltersQuery(UUID companyId,
                                            String title,
                                            UUID assigneeId,
                                            UUID reporterId,
                                            String severity,
                                            String status,
                                            UUID screenLocationId) {
    public GetIssueReportsByFiltersQuery {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
