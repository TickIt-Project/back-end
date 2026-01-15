package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllCompanyRolesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCompanyRoleByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CompanyRoleQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CompanyRoleRepository;

import java.util.List;
import java.util.Optional;

public class CompanyRoleQueryServiceImpl implements CompanyRoleQueryService {
    private final CompanyRoleRepository companyRoleRepository;

    public CompanyRoleQueryServiceImpl(CompanyRoleRepository companyRoleRepository) {
        this.companyRoleRepository = companyRoleRepository;
    }

    @Override
    public Optional<CompanyRole> handle(GetCompanyRoleByIdQuery query) {
        return companyRoleRepository.findById(query.companyRoleId());
    }

    @Override
    public List<CompanyRole> handle(GetAllCompanyRolesQuery query) {
        return companyRoleRepository.findAllByCompanyId(new CompanyID(query.companyId()));
    }
}
