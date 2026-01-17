package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllScreenLocationsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetScreenLocationByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ScreenLocationQueryService {
    Optional<ScreenLocation> handle(GetScreenLocationByIdQuery query);
    List<ScreenLocation> handle(GetAllScreenLocationsQuery query);
}
