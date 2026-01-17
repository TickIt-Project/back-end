package com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl;

import com.acme.tickit.tickitbackend.iam.interfaces.acl.CompanyContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExternalCompanyService {
    private final CompanyContextFacade companyContextFacade;

    public ExternalCompanyService(CompanyContextFacade companyContextFacade) {
        this.companyContextFacade = companyContextFacade;
    }

    public Boolean ExistsCompanyById(UUID companyId) {
        return this.companyContextFacade.ExistsCompanyById(companyId);
    }
}
