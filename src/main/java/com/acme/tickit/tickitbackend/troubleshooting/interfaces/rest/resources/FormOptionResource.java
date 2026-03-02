package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.UUID;

public record FormOptionResource(
        UUID id,
        String optionName
) {
}
