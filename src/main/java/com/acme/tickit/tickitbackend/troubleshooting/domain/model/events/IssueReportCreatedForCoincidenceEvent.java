package com.acme.tickit.tickitbackend.troubleshooting.domain.model.events;

import java.util.UUID;

/**
 * Domain event fired when an IssueReport is created with coincidenceAvailable=true.
 * Triggers the coincidence detection process to find matching reports and create IssueCoincidences.
 */
public record IssueReportCreatedForCoincidenceEvent(UUID issueReportId) {
}
