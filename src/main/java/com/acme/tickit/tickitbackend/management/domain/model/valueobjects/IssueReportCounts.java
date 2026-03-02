package com.acme.tickit.tickitbackend.management.domain.model.valueobjects;

/**
 * Value object holding issue report counts by status for a given assignee and time range.
 */
public record IssueReportCounts(
        int assigned,
        int open,
        int inProgress,
        int onHold,
        int closed,
        int cancelled
) {
    public IssueReportCounts {
        if (assigned < 0 || open < 0 || inProgress < 0 || onHold < 0 || closed < 0 || cancelled < 0) {
            throw new IllegalArgumentException("Counts cannot be negative");
        }
    }
}
