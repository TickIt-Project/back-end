package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.UUID;

public record CategorySelectResource(
        UUID id,
        String name
) {
}
