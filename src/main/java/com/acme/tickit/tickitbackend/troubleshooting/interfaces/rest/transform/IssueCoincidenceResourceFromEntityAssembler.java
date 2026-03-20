package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.IssueCoincidenceResource;

public class IssueCoincidenceResourceFromEntityAssembler {
    public static IssueCoincidenceResource toResourceFromEntity(IssueCoincidence entity) {
        return new IssueCoincidenceResource(
                entity.getId(),
                entity.getCompanyID() != null ? entity.getCompanyID().companyId() : null,
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getLanguage() != null ? entity.getLanguage().name() : null,
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getJiraSynced(),
                entity.getJiraSyncedAt()
        );
    }
}

