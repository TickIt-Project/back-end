package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCompanyRoleCommand;

import java.util.UUID;

public interface CompanyRoleCommandService {
    UUID handle(CreateCompanyRoleCommand command);
}
