package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateCategoryCommand;

import java.util.UUID;

public interface CategoryCommandService {
    UUID handle(CreateCategoryCommand command);
    UUID handle(UpdateCategoryCommand command);
}
