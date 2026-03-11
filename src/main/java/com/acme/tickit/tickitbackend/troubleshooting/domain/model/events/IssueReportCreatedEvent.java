package com.acme.tickit.tickitbackend.troubleshooting.domain.model.events;

import java.util.UUID;

/**
 * Domain event fired when an IssueReport is created.
 * Used e.g. by management context to create the first IssueReportHistory (status OPEN).
 */
public record IssueReportCreatedEvent(UUID issueReportId) {
}
