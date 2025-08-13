package com.acme.tickit.tickitbackend.shared.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record CompanyID(UUID companyId) {
    public CompanyID {
        if (companyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
    }
}
