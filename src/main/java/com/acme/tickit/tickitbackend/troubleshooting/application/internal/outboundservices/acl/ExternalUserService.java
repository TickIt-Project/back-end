package com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl;

import com.acme.tickit.tickitbackend.iam.interfaces.acl.UserContextFacade;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCompanyRoleByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CompanyRoleQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ExternalUserService {
    private final UserContextFacade userContextFacade;
    private final CompanyRoleQueryService companyRoleQueryService;

    public ExternalUserService(UserContextFacade userContextFacade, CompanyRoleQueryService companyRoleQueryService) {
        this.userContextFacade = userContextFacade;
        this.companyRoleQueryService = companyRoleQueryService;
    }

    public Boolean ExistsUserById(UUID userId) {
        return this.userContextFacade.ExistsUserById(userId);
    }

    public Optional<CompanyRole> GetCompanyRoleByUserId(UUID userId) {
        var user = this.userContextFacade.GetUserById(userId);
        if (user.isEmpty()) return Optional.empty();
        var companyRoleId = user.get().getCompanyRoleId().companyRoleId();
        if (companyRoleId == null) return Optional.empty();
        return companyRoleQueryService.handle(new GetCompanyRoleByIdQuery(companyRoleId));
    }
}
