package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryIdNotAcceptedException;

import java.util.UUID;

public record GetAllFieldsQuery(UUID categoryId) {
    public GetAllFieldsQuery {
        if (categoryId == null) {
            throw new CategoryIdNotAcceptedException();
        }
    }
}
