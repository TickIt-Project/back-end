package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueReport;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.IssueReportResource;

public class IssueReportResourceFromEntityAssembler {
    public static IssueReportResource toResourceFromEntity(IssueReport entity) {
        return new IssueReportResource(
                entity.getId(),
                entity.getCompanyId().companyId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getScreenLocation().getName(),
                entity.getCompanyRole().getName(),
                entity.getSeverity().name(),
                entity.getImgUrl(),
                entity.getStatus().name(),
                entity.getReporterId().userId(),
                entity.getAssigneeId().userId(),
                entity.getCreatedAt(),
                entity.getResolvedAt(),
                entity.getTicketOption(),
                entity.getIssueScreenUrl()
        );
    }
}
