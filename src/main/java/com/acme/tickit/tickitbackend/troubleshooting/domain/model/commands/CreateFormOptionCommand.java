package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.OptionNameNotAcceptedException;

import java.util.UUID;

public record CreateFormOptionCommand(UUID fieldId,
                                      String optionName) {
    public CreateFormOptionCommand {
        if (fieldId == null) {
            throw new FieldIdNotAcceptedException();
        }
        if (optionName == null || optionName.isEmpty()) {
            throw new OptionNameNotAcceptedException();
        }
    }
}
