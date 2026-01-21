package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllCompanyRolesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCompanyRoleByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CompanyRoleQueryService {
    Optional<CompanyRole> handle(GetCompanyRoleByIdQuery query);
    List<CompanyRole> handle(GetAllCompanyRolesQuery query);
}
