package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;


import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateCompanyCommand;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CreateCompanyResource;

public class CreateCompanyCommandFromResourceAssembler {
    public static CreateCompanyCommand toCommandFromResource(CreateCompanyResource resource) {
        return new CreateCompanyCommand(
                resource.companyName(),
                resource.jiraEmail(),
                resource.jiraPassword(),
                resource.isJiraActive(),
                resource.isSlackActive()
        );
    }
}
