package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.time.LocalDateTime;
import java.util.UUID;

public record IssueCoincidenceResource(
        UUID id,
        UUID companyId,
        String title,
        String description,
        String status,
        String language,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean jiraSynced,
        LocalDateTime jiraSyncedAt
) {
}

