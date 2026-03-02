package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateIssueReportCommand;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateIssueReportResource;

public class CreateIssueReportCommandFromResourceAssembler {
    public static CreateIssueReportCommand toCommandFromResource(CreateIssueReportResource resource, String imgUrlOverride) {
        return new CreateIssueReportCommand(
                resource.companyId(),
                resource.title(),
                resource.description(),
                resource.screenLocationId(),
                resource.severity(),
                imgUrlOverride != null ? imgUrlOverride : resource.imgUrl(),
                resource.reporterId(),
                resource.issueScreenUrl()
        );
    }
}
