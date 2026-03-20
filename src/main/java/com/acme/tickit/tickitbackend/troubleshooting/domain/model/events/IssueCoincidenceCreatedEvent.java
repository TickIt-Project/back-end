package com.acme.tickit.tickitbackend.troubleshooting.domain.model.events;

import java.util.UUID;

/**
 * Published when a new IssueCoincidence aggregate is persisted.
 * Management uses it to create the first IssueCoincidenceHistory row.
 */
public record IssueCoincidenceCreatedEvent(UUID issueCoincidenceId) {
}
