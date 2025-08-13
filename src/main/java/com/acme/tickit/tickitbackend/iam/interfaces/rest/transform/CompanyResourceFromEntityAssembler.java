package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.Company;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CompanyResource;

public class CompanyResourceFromEntityAssembler {
    public static CompanyResource toResourceFromEntity(Company entity) {
        return new CompanyResource(
                entity.getId(),
                entity.getJiraData().jiraEmail(),
                entity.getJiraData().jiraPassword(),
                entity.getIsJiraActive(),
                entity.getIsSlackActive(),
                entity.getCode().code()
        );
    }
}
