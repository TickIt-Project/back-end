package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllScreenLocationsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetScreenLocationByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.ScreenLocationQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.ScreenLocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScreenLocationQueryServiceImpl implements ScreenLocationQueryService {
    private final ScreenLocationRepository screenLocationRepository;

    public ScreenLocationQueryServiceImpl(ScreenLocationRepository screenLocationRepository) {
        this.screenLocationRepository = screenLocationRepository;
    }

    @Override
    public Optional<ScreenLocation> handle(GetScreenLocationByIdQuery query) {
        return screenLocationRepository.findById(query.screenLocationId());
    }

    @Override
    public List<ScreenLocation> handle(GetAllScreenLocationsQuery query) {
        return screenLocationRepository.findAllByCompanyId(new CompanyID(query.companyId()));
    }
}
