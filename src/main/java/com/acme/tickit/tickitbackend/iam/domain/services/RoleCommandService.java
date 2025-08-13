package com.acme.tickit.tickitbackend.iam.domain.services;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.SeedRolesCommand;

public interface RoleCommandService {
    void handle(SeedRolesCommand command);
}
