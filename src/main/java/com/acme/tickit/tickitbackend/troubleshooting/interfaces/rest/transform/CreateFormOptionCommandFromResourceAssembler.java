package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFormOptionCommand;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateFormOptionResource;

public class CreateFormOptionCommandFromResourceAssembler {
    public static CreateFormOptionCommand toCommandFromResource(CreateFormOptionResource resource) {
        return new CreateFormOptionCommand(
                resource.fieldId(),
                resource.optionName()
        );
    }
}
