package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.UUID;

public record CompanyRoleResource(
        UUID id,
        UUID CompanyId,
        String name
) {
}
