package com.acme.tickit.tickitbackend.iam.interfaces.rest.resources;

import java.util.UUID;

public record UserResource(
        UUID id,
        String name,
        String email,
        String password,
        Boolean notify_active,
        String role,
        String companyName,
        UUID companyRoleId,
        String language
) {
}
