package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import java.util.UUID;

public record UpdateIssueReportImageCommand(
        UUID issueReportId,
        String imageUrl
) {}
