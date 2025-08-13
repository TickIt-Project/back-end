package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.Company;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetCompanyByIdQuery;

import java.util.Optional;

public interface CompanyQueryService {
    Optional<Company> handle(GetCompanyByIdQuery query);
}
