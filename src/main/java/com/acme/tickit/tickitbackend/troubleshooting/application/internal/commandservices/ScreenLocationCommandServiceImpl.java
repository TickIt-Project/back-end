package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotFoundException;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl.ExternalCompanyService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.ScreenLocationAlreadyExistsException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.ScreenLocationNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateScreenLocationCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.ScreenLocationCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.ScreenLocationRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ScreenLocationCommandServiceImpl implements ScreenLocationCommandService {
    private final ScreenLocationRepository screenLocationRepository;
    private final ExternalCompanyService externalCompanyService;

    public ScreenLocationCommandServiceImpl(ScreenLocationRepository screenLocationRepository, ExternalCompanyService externalCompanyService) {
        this.screenLocationRepository = screenLocationRepository;
        this.externalCompanyService = externalCompanyService;
    }

    @Override
    public UUID handle(CreateScreenLocationCommand command) {
        if (!externalCompanyService.ExistsCompanyById(command.companyId()))
            throw new CompanyIdNotFoundException(command.companyId().toString());
        if (screenLocationRepository.existsByNameAndCompanyId(command.name(), new CompanyID(command.companyId())))
            throw new ScreenLocationAlreadyExistsException(command.name());
        if (screenLocationRepository.existsByUrlAndCompanyId(command.url(), new CompanyID(command.companyId())))
            throw new ScreenLocationAlreadyExistsException(command.url());
        var screen = new ScreenLocation(command);
        try {
            screenLocationRepository.save(screen);
        } catch (Exception e) {
            throw new ScreenLocationNotCreatedException(e.getMessage());
        }
        return screen.getId();
    }
}
