package com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRoleContextFacade {
    Optional<CompanyRole> GetCompanyRoleById(UUID companyRoleId);
}
