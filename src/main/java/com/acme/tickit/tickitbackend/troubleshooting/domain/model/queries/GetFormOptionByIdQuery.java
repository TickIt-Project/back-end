package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FormOptionIdNotAcceptedException;

import java.util.UUID;

public record GetFormOptionByIdQuery(UUID formOptionId) {
    public GetFormOptionByIdQuery {
        if (formOptionId == null) {
            throw new FormOptionIdNotAcceptedException();
        }
    }
}
