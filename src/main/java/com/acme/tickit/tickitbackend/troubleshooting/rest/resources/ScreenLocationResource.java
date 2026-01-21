package com.acme.tickit.tickitbackend.troubleshooting.rest.resources;

import java.util.UUID;

public record ScreenLocationResource(
        UUID id,
        UUID companyId,
        String name,
        String url
) {
}
