package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import java.util.UUID;

public record CompanyResource(
        UUID id,
        String jiraEmail,
        String jiraPassword,
        Boolean isJiraActive,
        Boolean isSlackActive,
        String code
) {
}
