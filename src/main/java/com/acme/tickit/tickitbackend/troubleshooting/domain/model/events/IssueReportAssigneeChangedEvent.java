package com.acme.tickit.tickitbackend.troubleshooting.domain.model.events;

import java.util.UUID;

/**
 * Domain event fired when an issue report's assignee is changed.
 */
public record IssueReportAssigneeChangedEvent(
        UUID issueReportId,
        UUID companyId,
        UUID previousAssigneeId,
        UUID newAssigneeId
) {
}
