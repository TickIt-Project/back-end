package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.SignInCommand;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {
    public static SignInCommand toCommandFromResource(SignInResource resource) {
        return new SignInCommand(
                resource.username(),
                resource.password()
        );
    }
}
