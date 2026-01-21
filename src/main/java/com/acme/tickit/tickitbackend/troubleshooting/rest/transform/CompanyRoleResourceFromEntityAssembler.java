package com.acme.tickit.tickitbackend.troubleshooting.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.rest.resources.CompanyRoleResource;

public class CompanyRoleResourceFromEntityAssembler {
    public static CompanyRoleResource toResourceFromEntity(CompanyRole entity) {
        return new CompanyRoleResource(
                entity.getId(),
                entity.getCompanyId().companyId(),
                entity.getName()
        );
    }
}
