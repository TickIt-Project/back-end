package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFormOptionCommand;

import java.util.UUID;

public interface FormOptionCommandService {
    UUID handle(CreateFormOptionCommand command);
}
