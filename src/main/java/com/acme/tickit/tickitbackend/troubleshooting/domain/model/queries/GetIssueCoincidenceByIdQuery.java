package com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.IssueCoincidenceIdNotAcceptedException;

import java.util.UUID;

public record GetIssueCoincidenceByIdQuery(UUID issueCoincidenceId) {
    public GetIssueCoincidenceByIdQuery {
        if (issueCoincidenceId == null) {
            throw new IssueCoincidenceIdNotAcceptedException();
        }
    }
}

