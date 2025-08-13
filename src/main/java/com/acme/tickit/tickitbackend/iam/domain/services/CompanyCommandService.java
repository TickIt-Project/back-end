package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.CreateCompanyCommand;

import java.util.UUID;

public interface CompanyCommandService {
    UUID handle(CreateCompanyCommand command);
}
