package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.RoleNameNotAcceptedException;

import java.util.UUID;

public record CreateCompanyRoleResource(
        UUID CompanyId,
        String name
) {
    public CreateCompanyRoleResource {
        if (CompanyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (name == null) {
            throw new RoleNameNotAcceptedException();
        }
    }
}
