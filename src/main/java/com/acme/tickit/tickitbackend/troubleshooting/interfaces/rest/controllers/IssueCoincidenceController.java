package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueCoincidencesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueCoincidenceByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.IssueCoincidence;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.IssueCoincidenceQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.IssueCoincidenceResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.IssueCoincidenceResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/issue-coincidences", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Issue Coincidences", description = "Available Issue Coincidences Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class IssueCoincidenceController {

    private final IssueCoincidenceQueryService issueCoincidenceQueryService;

    public IssueCoincidenceController(IssueCoincidenceQueryService issueCoincidenceQueryService) {
        this.issueCoincidenceQueryService = issueCoincidenceQueryService;
    }

    @GetMapping("/{issueCoincidenceId}")
    @Operation(summary = "Get Issue Coincidence by id", description = "Get Issue Coincidence by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Coincidence found"),
            @ApiResponse(responseCode = "404", description = "Issue Coincidence not found")
    })
    public ResponseEntity<IssueCoincidenceResource> getIssueCoincidenceById(@PathVariable UUID issueCoincidenceId) {
        var query = new GetIssueCoincidenceByIdQuery(issueCoincidenceId);
        var coincidence = issueCoincidenceQueryService.handle(query);
        if (coincidence.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(IssueCoincidenceResourceFromEntityAssembler.toResourceFromEntity(coincidence.get()));
    }

    @GetMapping("/{companyId}/issue-coincidences")
    @Operation(summary = "Get all Issue Coincidences", description = "Get all available Issue Coincidences in the system by companyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Coincidences retrieved successfully.")
    })
    public ResponseEntity<List<IssueCoincidenceResource>> getAllIssueCoincidences(@PathVariable UUID companyId) {
        var query = new GetAllIssueCoincidencesQuery(companyId);
        List<IssueCoincidence> coincidences = issueCoincidenceQueryService.handle(query);
        var resources = coincidences.stream()
                .map(IssueCoincidenceResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(resources);
    }
}

