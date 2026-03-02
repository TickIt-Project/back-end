package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIdNotAcceptedException;

import java.util.UUID;

public record GetFieldByIdQuery(UUID fieldId) {
    public GetFieldByIdQuery {
        if (fieldId == null) {
            throw new FieldIdNotAcceptedException();
        }
    }
}
