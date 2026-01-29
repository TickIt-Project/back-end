package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportAssigneeCommand;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.UpdateIssueReportAssigneeResource;

import java.util.UUID;

public class UpdateIssueReportAssigneeCommandFromResourceAssembler {
    public static UpdateIssueReportAssigneeCommand toCommandFromResource(UUID issueReportId, UpdateIssueReportAssigneeResource resource) {
        return new UpdateIssueReportAssigneeCommand(
                issueReportId,
                resource.assigneeId()
        );
    }
}
