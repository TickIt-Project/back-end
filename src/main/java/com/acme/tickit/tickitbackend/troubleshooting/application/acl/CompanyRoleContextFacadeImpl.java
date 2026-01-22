package com.acme.tickit.tickitbackend.troubleshooting.application.acl;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCompanyRoleByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CompanyRoleQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.acl.CompanyRoleContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyRoleContextFacadeImpl implements CompanyRoleContextFacade {
    private final CompanyRoleQueryService companyRoleQueryService;

    public CompanyRoleContextFacadeImpl(CompanyRoleQueryService companyRoleQueryService) {
        this.companyRoleQueryService = companyRoleQueryService;
    }

    @Override
    public Optional<CompanyRole> GetCompanyRoleById(UUID companyRoleId) {
        var getCompanyRole = new GetCompanyRoleByIdQuery(companyRoleId);
        return companyRoleQueryService.handle(getCompanyRole);
    }
}
