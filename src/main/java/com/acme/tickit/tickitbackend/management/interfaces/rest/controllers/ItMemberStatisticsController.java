package com.acme.tickit.tickitbackend.management.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.management.domain.model.queries.GetAllItMemberStatisticsByCompanyIdQuery;
import com.acme.tickit.tickitbackend.management.domain.services.ItMemberStatisticsCommandService;
import com.acme.tickit.tickitbackend.management.domain.services.ItMemberStatisticsQueryService;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.CreateItMemberStatisticsResource;
import com.acme.tickit.tickitbackend.management.interfaces.rest.resources.ItMemberStatisticsResource;
import com.acme.tickit.tickitbackend.management.interfaces.rest.transform.ItMemberStatisticsResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/it-member-statistics", produces = APPLICATION_JSON_VALUE)
@Tag(name = "IT Member Statistics", description = "IT Member Statistics Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class ItMemberStatisticsController {

    private final ItMemberStatisticsCommandService itMemberStatisticsCommandService;
    private final ItMemberStatisticsQueryService itMemberStatisticsQueryService;

    public ItMemberStatisticsController(ItMemberStatisticsCommandService itMemberStatisticsCommandService,
                                        ItMemberStatisticsQueryService itMemberStatisticsQueryService) {
        this.itMemberStatisticsCommandService = itMemberStatisticsCommandService;
        this.itMemberStatisticsQueryService = itMemberStatisticsQueryService;
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "Get all IT member statistics", description = "Get all IT member statistics for the given company.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully")
    })
    public ResponseEntity<List<ItMemberStatisticsResource>> getAllByCompanyId(@PathVariable UUID companyId) {
        var query = new GetAllItMemberStatisticsByCompanyIdQuery(companyId);
        var statistics = itMemberStatisticsQueryService.handle(query);
        var resources = statistics.stream()
                .map(ItMemberStatisticsResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Create/update IT member statistics", description = "Creates or updates statistics for the given IT head or IT member. Uses issue reports modified from Sunday of current week to now.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics created or updated"),
            @ApiResponse(responseCode = "400", description = "Invalid user (not found or not IT head/member)")
    })
    public ResponseEntity<ItMemberStatisticsResource> createOrUpdate(@RequestBody CreateItMemberStatisticsResource resource) {
        var stats = itMemberStatisticsCommandService.createOrUpdateForUser(resource.userId());
        if (stats.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        var resourceResult = ItMemberStatisticsResourceFromEntityAssembler.toResourceFromEntity(stats.get());
        return ResponseEntity.ok(resourceResult);
    }

    // TODO: Improve the structure of this endpoint as a create
}
