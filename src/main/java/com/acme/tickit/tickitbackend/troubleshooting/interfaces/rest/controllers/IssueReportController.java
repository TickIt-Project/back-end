package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueReportsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueReportByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.IssueReportCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.IssueReportQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.IssueReportResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateIssueReportResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.UpdateIssueReportAssigneeResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.UpdateIssueReportStatusResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.IssueReportResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CreateIssueReportCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.UpdateIssueReportAssigneeCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.UpdateIssueReportStatusCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/issue-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Issue Reports", description = "Available Issue Reports Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class IssueReportController {
    private final IssueReportCommandService issueReportCommandService;
    private final IssueReportQueryService issueReportQueryService;

    public IssueReportController(IssueReportCommandService issueReportCommandService, IssueReportQueryService issueReportQueryService) {
        this.issueReportCommandService = issueReportCommandService;
        this.issueReportQueryService = issueReportQueryService;
    }

    @PostMapping("/issue-report")
    @Operation(summary = "Create a new Issue Report", description = "Create a new Issue Report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Issue Report created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")})
    public ResponseEntity<IssueReportResource> createIssueReport(@RequestBody CreateIssueReportResource resource) {
        var createCommand = CreateIssueReportCommandFromResourceAssembler.toCommandFromResource(resource);
        var issueReportId = issueReportCommandService.handle(createCommand);
        if (issueReportId == null) return ResponseEntity.badRequest().build();
        var getIssueReportByIdQuery = new GetIssueReportByIdQuery(issueReportId);
        var issueReportItem = issueReportQueryService.handle(getIssueReportByIdQuery);
        if (issueReportItem.isEmpty()) return ResponseEntity.notFound().build();
        var issueReportEntity = issueReportItem.get();
        var issueReportResource = IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReportEntity);
        return new ResponseEntity<>(issueReportResource, HttpStatus.CREATED);
    }

    @GetMapping("/{issueReportId}")
    @Operation(summary = "Get Issue Report by id", description = "Get Issue Report by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Report found"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")})
    public ResponseEntity<IssueReportResource> getIssueReportById(@PathVariable UUID issueReportId) {
        var getIssueReportByIdQuery = new GetIssueReportByIdQuery(issueReportId);
        var issueReport = issueReportQueryService.handle(getIssueReportByIdQuery);
        if (issueReport.isEmpty()) return ResponseEntity.notFound().build();
        var issueReportEntity = issueReport.get();
        var issueReportResource = IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReportEntity);
        return ResponseEntity.ok(issueReportResource);
    }

    @GetMapping("/{companyId}/issue-reports")
    @Operation(summary = "Get all Issue Reports", description = "Get all available Issue Reports in the system by companyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Reports retrieved successfully.")})
    public ResponseEntity<List<IssueReportResource>> getAllIssueReports(@PathVariable UUID companyId) {
        var getAllIssueReportsQuery = new GetAllIssueReportsQuery(companyId);
        var issueReports = issueReportQueryService.handle(getAllIssueReportsQuery);
        var issueReportResources = issueReports.stream()
                .map(IssueReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(issueReportResources);
    }

    @PatchMapping("/{issueReportId}/status")
    @Operation(summary = "Update issue report status", description = "Updates the status of a issue report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Report status updated"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")
    })
    public ResponseEntity<IssueReportResource> updateIssueReportStatus(@PathVariable UUID issueReportId, @RequestBody UpdateIssueReportStatusResource resource) {
        var command = UpdateIssueReportStatusCommandFromResourceAssembler.toCommandFromResource(issueReportId, resource);
        var updateIssueReport = issueReportCommandService.handle(command);
        if (updateIssueReport.isEmpty()) return ResponseEntity.badRequest().build();
        var issueReportEntity = updateIssueReport.get();
        var issueReportResource = IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReportEntity);
        return ResponseEntity.ok(issueReportResource);
    }

    @PatchMapping("/{issueReportId}/assignee")
    @Operation(summary = "Update issue report assignee", description = "Updates the assignee of a issue report")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Report assignee updated"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")
    })
    public ResponseEntity<IssueReportResource> updateIssueReportAssignee(@PathVariable UUID issueReportId, @RequestBody UpdateIssueReportAssigneeResource resource) {
        var command = UpdateIssueReportAssigneeCommandFromResourceAssembler.toCommandFromResource(issueReportId, resource);
        var updateIssueReport = issueReportCommandService.handle(command);
        if (updateIssueReport.isEmpty()) return ResponseEntity.badRequest().build();
        var issueReportEntity = updateIssueReport.get();
        var issueReportResource = IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReportEntity);
        return ResponseEntity.ok(issueReportResource);
    }
}
