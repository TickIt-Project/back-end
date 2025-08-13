package com.acme.tickit.tickitbackend.iam.domain.model.queries;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserIdNullException;

import java.util.UUID;

public record GetUserByIdQuery(UUID userId) {
    public GetUserByIdQuery {
        if (userId == null) {
            throw new UserIdNullException();
        }
    }
}
