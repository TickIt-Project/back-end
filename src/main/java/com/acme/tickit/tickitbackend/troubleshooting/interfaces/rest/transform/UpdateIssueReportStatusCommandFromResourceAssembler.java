package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateIssueReportStatusCommand;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.UpdateIssueReportStatusResource;

import java.util.UUID;

public class UpdateIssueReportStatusCommandFromResourceAssembler {
    public static UpdateIssueReportStatusCommand toCommandFromResource(UUID issueReportId, UpdateIssueReportStatusResource resource) {
        return new UpdateIssueReportStatusCommand(
                issueReportId,
                resource.status()
        );
    }
}
