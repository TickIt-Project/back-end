package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryDescriptionNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;

import java.util.List;
import java.util.UUID;

public record CreateCategoryCommand(UUID companyId, String name, String description, List<CategoryField> fields) {
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

    /** Fields to create and add to this category; may be null (treated as empty). */
    public List<CategoryField> fields() {
        return fields == null ? List.of() : fields;
    }
}
