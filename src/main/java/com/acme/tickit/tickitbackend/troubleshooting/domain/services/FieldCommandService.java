package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFieldCommand;

import java.util.UUID;

public interface FieldCommandService {
    UUID handle(CreateFieldCommand command);
}
