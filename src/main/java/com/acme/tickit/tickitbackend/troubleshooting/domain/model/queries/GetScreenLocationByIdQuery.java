package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.ScreenLocationIdNotAcceptedException;

import java.util.UUID;

public record GetScreenLocationByIdQuery(UUID screenLocationId) {
    public GetScreenLocationByIdQuery {
        if (screenLocationId == null) {
            throw new ScreenLocationIdNotAcceptedException();
        }
    }
}
