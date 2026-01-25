package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.AssigneeUserNotAcceptedException;

import java.util.UUID;

public record UpdateIssueReportAssigneeResource(UUID assigneeId) {
    public UpdateIssueReportAssigneeResource {
        if (assigneeId == null) {
            throw new AssigneeUserNotAcceptedException();
        }
    }
}
