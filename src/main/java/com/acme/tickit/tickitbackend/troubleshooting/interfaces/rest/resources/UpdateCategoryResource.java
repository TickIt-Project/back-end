package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryDescriptionNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNameNotAcceptedException;

import java.util.List;

public record UpdateCategoryResource(
        String name,
        String description,
        List<FieldDefinitionResource> fields
) {
    public UpdateCategoryResource {
        if (name == null || name.isEmpty()) {
            throw new CategoryNameNotAcceptedException();
        }
        if (description == null || description.isEmpty()) {
            throw new CategoryDescriptionNotAcceptedException();
        }
        if (fields == null) {
            fields = List.of();
        }
    }
}

