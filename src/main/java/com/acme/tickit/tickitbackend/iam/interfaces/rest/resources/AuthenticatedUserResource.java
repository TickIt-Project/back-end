package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import java.util.UUID;

public record AuthenticatedUserResource(
        UUID companyId,
        UUID userId,
        String email,
        String token
) {}