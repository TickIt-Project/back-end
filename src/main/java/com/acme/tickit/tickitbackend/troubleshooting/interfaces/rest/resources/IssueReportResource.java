package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.UUID;

public record IssueReportResource(
        UUID id,
        UUID companyId,
        String title,
        String description,
        String screenLocationName,
        String companyRoleName,
        String severity,
        String imgUrl,
        String status,
        UUID reporterId,
        UUID assigneeId,
        LocalDateTime createdAt,
        LocalDateTime resolvedAt,
        Boolean ticketOption,
        String issueScreenUrl,
        Boolean coincidenceAvailable,
        String language
) {
}
