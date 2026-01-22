package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.UUID;

public record ScreenLocationResource(
        UUID id,
        UUID companyId,
        String name,
        String url
) {
}
