package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllScreenLocationsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetScreenLocationByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.ScreenLocationCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.ScreenLocationQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateScreenLocationResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.ScreenLocationResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CreateScreenLocationCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.ScreenLocationResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/screen-locations", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Screen Locations", description = "Available Screen Locations Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ScreenLocationController {
    private final ScreenLocationCommandService screenLocationCommandService;
    private final ScreenLocationQueryService screenLocationQueryService;

    public ScreenLocationController(ScreenLocationCommandService screenLocationCommandService, ScreenLocationQueryService screenLocationQueryService) {
        this.screenLocationCommandService = screenLocationCommandService;
        this.screenLocationQueryService = screenLocationQueryService;
    }

    @PostMapping("/screen-location")
    @Operation(summary = "Create a new Screen Location", description = "Create a new Screen Location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Screen Location created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Screen Location not found")})
    public ResponseEntity<ScreenLocationResource> createScreenLocation(@RequestBody CreateScreenLocationResource resource) {
        var createCommand = CreateScreenLocationCommandFromResourceAssembler.toCommandFromResource(resource);
        var screenLocationId = screenLocationCommandService.handle(createCommand);
        if (screenLocationId == null) return ResponseEntity.badRequest().build();
        var getScreenLocationByIdQuery = new GetScreenLocationByIdQuery(screenLocationId);
        var screenLocationItem = screenLocationQueryService.handle(getScreenLocationByIdQuery);
        if (screenLocationItem.isEmpty()) return ResponseEntity.notFound().build();
        var screenLocationEntity = screenLocationItem.get();
        var screenLocationResource = ScreenLocationResourceFromEntityAssembler.toResourceFromEntity(screenLocationEntity);
        return new ResponseEntity<>(screenLocationResource, HttpStatus.CREATED);
    }

    @GetMapping("/{screenLocationId}")
    @Operation(summary = "Get Screen Location by id", description = "Get Screen Location by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen Location found"),
            @ApiResponse(responseCode = "404", description = "Screen Location not found")})
    public ResponseEntity<ScreenLocationResource> getScreenLocationById(@PathVariable UUID screenLocationId) {
        var getScreenLocationByIdQuery = new GetScreenLocationByIdQuery(screenLocationId);
        var screenLocation = screenLocationQueryService.handle(getScreenLocationByIdQuery);
        if (screenLocation.isEmpty()) return ResponseEntity.notFound().build();
        var screenLocationEntity = screenLocation.get();
        var screenLocationResource = ScreenLocationResourceFromEntityAssembler.toResourceFromEntity(screenLocationEntity);
        return ResponseEntity.ok(screenLocationResource);
    }

    @GetMapping("/{companyId}/screen-locations")
    @Operation(summary = "Get all Screen Locations", description = "Get all available Screen Locations in the system by companyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Screen Locations retrieved successfully.")})
    public ResponseEntity<List<ScreenLocationResource>> getAllScreenLocations(@PathVariable UUID companyId) {
        var getAllScreenLocationQuery = new GetAllScreenLocationsQuery(companyId);
        var screenLocations = screenLocationQueryService.handle(getAllScreenLocationQuery);
        var screenLocationResources = screenLocations.stream()
                .map(ScreenLocationResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(screenLocationResources);
    }
}
