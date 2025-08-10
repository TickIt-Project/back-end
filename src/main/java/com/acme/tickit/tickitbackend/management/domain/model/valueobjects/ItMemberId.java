package com.acme.tickit.tickitbackend.management.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.management.domain.exceptions.ItMemberIdNotAcceptedException;
import jakarta.persistence.Embeddable;

import java.util.UUID;
@Embeddable
public record ItMemberId(UUID itMemberId) {
    public ItMemberId {
        if (itMemberId == null) {
            throw new ItMemberIdNotAcceptedException();
        }
    }
}
