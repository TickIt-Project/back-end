package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.RoleNameNotAcceptedException;

import java.util.UUID;

public record CreateCompanyRoleCommand(
        UUID CompanyId,
        String name
) {
    public CreateCompanyRoleCommand {
        if (CompanyId == null) {
            throw new CompanyIdNotAcceptedException();
        }
        if (name == null) {
            throw new RoleNameNotAcceptedException();
        }
    }
}
