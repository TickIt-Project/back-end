package com.acme.tickit.tickitbackend.iam.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User entity) {
        return new UserResource(
                entity.getId(),
                entity.getPersonalData().name(),
                entity.getPersonalData().email(),
                entity.getPassword().password(),
                entity.getNotify_active(),
                entity.getRole().getStringName(),
                entity.getCompany().getCompanyName(),
                entity.getCompanyRoleId() != null
                        ? entity.getCompanyRoleId().companyRoleId()
                        : null
        );
    }
}
