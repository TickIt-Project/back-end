package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CategorySelectResource;

public class CategorySelectResourceFromEntityAssembler {
    public static CategorySelectResource toResourceFromEntity(Category entity) {
        return new CategorySelectResource(
                entity.getId(),
                entity.getName()
        );
    }
}
