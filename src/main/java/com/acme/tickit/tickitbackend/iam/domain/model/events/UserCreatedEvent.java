package com.acme.tickit.tickitbackend.iam.domain.model.events;

import com.acme.tickit.tickitbackend.iam.domain.model.valueobjects.Roles;

import java.util.UUID;

/**
 * Domain event fired when a user is created.
 * Contains the data needed by consuming contexts to react accordingly.
 */
public record UserCreatedEvent(UUID userId, UUID companyId, Roles role) {
}
