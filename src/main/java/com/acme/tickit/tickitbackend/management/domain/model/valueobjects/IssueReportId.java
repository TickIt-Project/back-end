package com.acme.tickit.tickitbackend.management.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportIdNotAcceptedException;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record IssueReportId(UUID issueReportId) {
    public IssueReportId {
        if (issueReportId == null) {
            throw new IssueReportIdNotAcceptedException();
        }
    }
}
