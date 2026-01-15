package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CompanyRoleAlreadyExistsException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CompanyRoleNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCompanyRoleCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.CompanyRole;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CompanyRoleCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CompanyRoleRepository;

import java.util.UUID;

public class CompanyRoleCommandServiceImpl implements CompanyRoleCommandService {
    private final CompanyRoleRepository companyRoleRepository;

    public CompanyRoleCommandServiceImpl(CompanyRoleRepository companyRoleRepository) {
        this.companyRoleRepository = companyRoleRepository;
    }

    @Override
    public UUID handle(CreateCompanyRoleCommand command) {
        if (companyRoleRepository.findByNameAndCompanyId(command.name(), new CompanyID(command.CompanyId())))
            throw new CompanyRoleAlreadyExistsException(command.name());
        var role = new CompanyRole(command);
        try {
            companyRoleRepository.save(role);
        } catch (Exception e) {
            throw new CompanyRoleNotCreatedException(e.getMessage());
        }
        return role.getId();
    }
}
