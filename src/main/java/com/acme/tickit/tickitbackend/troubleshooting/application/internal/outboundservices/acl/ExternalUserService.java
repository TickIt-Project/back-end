package com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl;

import com.acme.tickit.tickitbackend.iam.interfaces.acl.UserContextFacade;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalUserService {
    private final UserContextFacade userContextFacade;

    public ExternalUserService(UserContextFacade userContextFacade) {
        this.userContextFacade = userContextFacade;
    }

    public Boolean ExistsUserById(UUID userId) {
        return this.userContextFacade.ExistsUserById(userId);
    }

    public CompanyRole GetCompanyRoleByUserId(UUID userId) {
        return this.userContextFacade.GetUserById(userId).isPresent() ?
                this.userContextFacade.GetUserById(userId).get().getCompanyRole() : null;
    }
}
