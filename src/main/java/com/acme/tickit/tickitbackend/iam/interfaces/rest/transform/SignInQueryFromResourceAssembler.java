package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.queries.SignInQuery;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.SignInResource;

public class SignInQueryFromResourceAssembler {
    public static SignInQuery toQueryFromResource(SignInResource resource) {
        return new SignInQuery(
                resource.username(),
                resource.password()
        );
    }
}
