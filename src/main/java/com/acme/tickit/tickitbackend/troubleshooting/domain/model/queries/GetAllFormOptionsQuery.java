package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIdNotAcceptedException;

import java.util.UUID;

public record GetAllFormOptionsQuery(UUID fieldId) {
    public GetAllFormOptionsQuery {
        if (fieldId == null) {
            throw new FieldIdNotAcceptedException();
        }
    }
}
