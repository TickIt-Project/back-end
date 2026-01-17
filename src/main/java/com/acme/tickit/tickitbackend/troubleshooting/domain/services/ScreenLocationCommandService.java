package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateScreenLocationCommand;

import java.util.UUID;

public interface ScreenLocationCommandService {
    UUID handle(CreateScreenLocationCommand command);
}
