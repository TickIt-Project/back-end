package com.acme.tickit.tickitbackend.iam.application.internal.eventhandlers;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.SeedRolesCommand;
import com.acme.tickit.tickitbackend.iam.domain.services.RoleCommandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class ApplicationReadyEventHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationReadyEventHandler.class);
    private final RoleCommandService roleCommandService;

    public ApplicationReadyEventHandler(RoleCommandService roleCommandService) {
        this.roleCommandService = roleCommandService;
    }

    @EventListener
    public void on(ApplicationReadyEvent event) {
        LOGGER.info("Application ready event received. Seeding initial data...");
        try {
            roleCommandService.handle(new SeedRolesCommand());
            LOGGER.info("Initial roles seeded successfully");
        } catch (Exception e) {
            LOGGER.error("Error seeding initial data", e);
        }
    }
}
