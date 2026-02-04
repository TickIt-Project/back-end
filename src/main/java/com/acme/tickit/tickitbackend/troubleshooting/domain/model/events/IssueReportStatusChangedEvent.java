package com.acme.tickit.tickitbackend.troubleshooting.domain.model.events;

import java.util.UUID;

/**
 * Domain event fired when an issue report's status is changed.
 */
public record IssueReportStatusChangedEvent(
        UUID issueReportId,
        UUID companyId,
        String previousStatus,
        String newStatus,
        UUID assigneeId
) {
}
