package com.acme.tickit.tickitbackend.troubleshooting.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateScreenLocationCommand;
import com.acme.tickit.tickitbackend.troubleshooting.rest.resources.CreateScreenLocationResource;

public class CreateScreenLocationCommandFromResourceAssembler {
    public static CreateScreenLocationCommand toCommandFromResource(CreateScreenLocationResource resource) {
        return new CreateScreenLocationCommand(
                resource.companyId(),
                resource.name(),
                resource.url()
        );
    }
}
