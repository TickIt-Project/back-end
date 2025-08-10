package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.UserIdNotAcceptedException;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record UserID(UUID userId) {
    public UserID {
        if (userId == null) {
            throw new UserIdNotAcceptedException();
        }
    }
}
