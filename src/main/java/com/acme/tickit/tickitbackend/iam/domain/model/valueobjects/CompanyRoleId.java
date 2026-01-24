package com.acme.tickit.tickitbackend.iam.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public record CompanyRoleId(UUID companyRoleId) {
    public CompanyRoleId {}
}
