package com.acme.tickit.tickitbackend.management.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueCoincidenceIdNotAcceptedException;

import java.util.UUID;

public record IssueCoincidenceId(UUID issueCoincidenceId) {
    public IssueCoincidenceId {
        if (issueCoincidenceId == null) {
            throw new IssueCoincidenceIdNotAcceptedException();
        }
    }
}
