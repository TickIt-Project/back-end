package com.acme.tickit.tickitbackend.iam.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.CompanyCodeNotAcceptedException;
import jakarta.persistence.Embeddable;

@Embeddable
public record CompanyCode(String code) {
    public CompanyCode {
        if (code == null || code.isEmpty()) {
            throw new CompanyCodeNotAcceptedException();
        }
    }
}
