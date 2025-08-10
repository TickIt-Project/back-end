package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.KeywordNotAcceptedException;
import jakarta.persistence.Embeddable;

@Embeddable
public record Keyword(String keyword) {
    public Keyword {
        if (keyword == null || keyword.isEmpty()) {
            throw new KeywordNotAcceptedException(keyword);
        }
    }
}
