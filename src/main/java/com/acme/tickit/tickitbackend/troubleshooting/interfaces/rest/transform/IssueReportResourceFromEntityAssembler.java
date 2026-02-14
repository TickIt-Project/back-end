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
                entity.getScreenLocation() != null
                        ? entity.getScreenLocation().getName()
                        : null,
                entity.getCompanyRole() != null
                        ? entity.getCompanyRole().getName()
                        : null,
                entity.getSeverity().name(),
                entity.getImageUrl(),
                entity.getStatus().name(),
                entity.getReporterId().userId(),
                entity.getAssigneeId() != null
                        ? entity.getAssigneeId().userId()
                        : null,
                entity.getCreatedAt(),
                entity.getResolvedAt(),
                entity.getTicketOption(),
                entity.getIssueScreenUrl() != null ? entity.getIssueScreenUrl() : "",
                entity.getCoincidenceAvailable(),
                entity.getLanguage().name()
        );
    }
}
