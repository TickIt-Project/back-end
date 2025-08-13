package com.acme.tickit.tickitbackend.iam.application.internal.queryservices;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.Company;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetCompanyByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.CompanyQueryService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.CompanyRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CompanyQueryServiceImpl implements CompanyQueryService {
    private final CompanyRepository companyRepository;

    public CompanyQueryServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Optional<Company> handle(GetCompanyByIdQuery query) {
        return companyRepository.findById(query.companyId());
    }
}
