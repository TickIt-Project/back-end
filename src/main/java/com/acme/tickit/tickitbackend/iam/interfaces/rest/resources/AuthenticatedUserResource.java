package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import java.util.UUID;

public record AuthenticatedUserResource(
        String token,
        UUID userId,
        String username,
        String role,
        String companyId
) {}