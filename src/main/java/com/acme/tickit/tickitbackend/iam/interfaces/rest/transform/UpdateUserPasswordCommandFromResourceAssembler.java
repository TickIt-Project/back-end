package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.UpdateUserPasswordCommand;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.UpdateUserPasswordResource;

import java.util.UUID;

public class UpdateUserPasswordCommandFromResourceAssembler {
    public static UpdateUserPasswordCommand toCommandFromResource(UUID tenantId, UpdateUserPasswordResource resource) {
        return new UpdateUserPasswordCommand(
                tenantId,
                resource.userId(),
                resource.oldPassword(),
                resource.newPassword()
        );
    }
}
