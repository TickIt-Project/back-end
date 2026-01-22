package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportIdNotAcceptedException;

import java.util.UUID;

public record GetIssueReportByIdQuery(UUID issueReportId) {
    public GetIssueReportByIdQuery {
        if (issueReportId == null) {
            throw new IssueReportIdNotAcceptedException();
        }
    }
}
