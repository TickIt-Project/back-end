package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryIdNotAcceptedException;

import java.util.UUID;

public record GetCategoryByIdQuery(UUID categoryId) {
    public GetCategoryByIdQuery {
        if (categoryId == null) {
            throw new CategoryIdNotAcceptedException();
        }
    }
}
