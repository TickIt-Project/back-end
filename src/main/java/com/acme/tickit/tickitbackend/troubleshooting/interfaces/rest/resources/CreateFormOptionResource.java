package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.OptionNameNotAcceptedException;

import java.util.UUID;

public record CreateFormOptionResource(
        UUID fieldId,
        String optionName
) {
    public CreateFormOptionResource {
        if (fieldId == null) {
            throw new FieldIdNotAcceptedException();
        }
        if (optionName == null || optionName.isEmpty()) {
            throw new OptionNameNotAcceptedException();
        }
    }
}
