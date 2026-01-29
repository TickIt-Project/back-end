package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCompanyRoleCommand;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateCompanyRoleResource;

public class CreateCompanyRoleCommandFromResourceAssembler {
    public static CreateCompanyRoleCommand toCommandFromResource(CreateCompanyRoleResource resource) {
        return new CreateCompanyRoleCommand(
                resource.CompanyId(),
                resource.name()
        );
    }
}
