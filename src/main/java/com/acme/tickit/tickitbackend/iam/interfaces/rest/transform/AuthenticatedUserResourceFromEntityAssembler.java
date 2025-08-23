package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

    public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
        return new AuthenticatedUserResource(
                token,
                user.getId(),
                user.getPersonalData().name(),
                user.getRole().getStringName(),
                user.getCompany().getId().toString()
        );
    }
}
