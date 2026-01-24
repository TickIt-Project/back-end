package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.AssigneeUserNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueReportIdNotAcceptedException;

import java.util.UUID;

public record UpdateIssueReportAssigneeCommand(UUID issueReportId, UUID assigneeId) {
    public UpdateIssueReportAssigneeCommand {
        if (issueReportId == null) {
            throw new IssueReportIdNotAcceptedException();
        }
        if (assigneeId == null) {
            throw new AssigneeUserNotAcceptedException();
        }
    }
}
