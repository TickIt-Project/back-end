package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

public record CategoryResource(
        UUID id,
        String name,
        String description,
        List<FieldResource> fields
) {
}
