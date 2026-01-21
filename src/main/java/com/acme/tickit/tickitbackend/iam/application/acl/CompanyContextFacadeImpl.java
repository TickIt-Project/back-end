package com.acme.tickit.tickitbackend.iam.application.acl;

import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetCompanyByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.CompanyQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.acl.CompanyContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CompanyContextFacadeImpl implements CompanyContextFacade {
    private final CompanyQueryService companyQueryService;

    public CompanyContextFacadeImpl(CompanyQueryService companyQueryService) {
        this.companyQueryService = companyQueryService;
    }

    @Override
    public Boolean ExistsCompanyById(UUID companyId) {
        var getCompany = new GetCompanyByIdQuery(companyId);
        var company = companyQueryService.handle(getCompany);
        return company.isPresent();
    }
}
