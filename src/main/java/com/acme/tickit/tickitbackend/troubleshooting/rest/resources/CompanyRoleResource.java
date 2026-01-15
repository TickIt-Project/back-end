package com.acme.tickit.tickitbackend.troubleshooting.rest.resources;

import java.util.UUID;

public record CompanyRoleResource(
        UUID id,
        UUID CompanyId,
        String name
) {
}
