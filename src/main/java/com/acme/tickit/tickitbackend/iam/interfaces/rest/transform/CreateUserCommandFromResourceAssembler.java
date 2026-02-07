package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateUserCommand;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    public static CreateUserCommand toCommandFromResource(CreateUserResource resource) {
        return toCommandFromResource(resource, resource.profileImageUrl());
    }

    public static CreateUserCommand toCommandFromResource(CreateUserResource resource, String profileImageUrlOverride) {
        return new CreateUserCommand(
                resource.username(),
                resource.email(),
                resource.role(),
                resource.password(),
                resource.notify_active(),
                resource.companyCode(),
                resource.companyRoleId(),
                resource.language(),
                profileImageUrlOverride != null ? profileImageUrlOverride : resource.profileImageUrl()
        );
    }
}

// CHECK THIS LATER
