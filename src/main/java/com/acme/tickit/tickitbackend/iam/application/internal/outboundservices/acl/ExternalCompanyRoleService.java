package com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.acl;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl.CompanyRoleContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ExternalCompanyRoleService {
    private final CompanyRoleContextFacade companyRoleContextFacade;

    public ExternalCompanyRoleService(CompanyRoleContextFacade companyRoleContextFacade) {
        this.companyRoleContextFacade = companyRoleContextFacade;
    }

    public Boolean ExistsCompanyRoleById(UUID companyRoleId) {
        return companyRoleContextFacade.ExistsCompanyRoleById(companyRoleId);
    }
}
