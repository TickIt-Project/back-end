package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;

import java.util.List;
import java.util.UUID;

public record UpdateCategoryCommand(UUID categoryId, String name, String description, List<CategoryField> fields) {
    public UpdateCategoryCommand {
        if (categoryId == null) {
            throw new CategoryIdNotAcceptedException();
        }
        if (name == null || name.isEmpty()) {
            throw new CategoryNameNotAcceptedException();
        }
    }

    public List<CategoryField> fields() {
        return fields == null ? List.of() : fields;
    }
}
