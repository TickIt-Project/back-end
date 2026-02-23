package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryDescriptionNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNameNotAcceptedException;

import java.util.UUID;

public record CreateCategoryCommand(UUID companyId, String name, String description) {
    public CreateCategoryCommand {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (name == null || name.isEmpty()) {
            throw new CategoryNameNotAcceptedException();
        }
        if (description == null || description.isEmpty()) {
            throw new CategoryDescriptionNotAcceptedException();
        }
    }
}
