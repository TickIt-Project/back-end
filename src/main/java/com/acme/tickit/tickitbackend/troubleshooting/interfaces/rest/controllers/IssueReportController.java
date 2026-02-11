package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllIssueReportsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueReportByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetIssueReportsByFiltersQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Severity;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.Status;
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
import com.acme.tickit.tickitbackend.shared.application.external.ImageStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
    private final ImageStorageService imageStorageService;
    private final ObjectMapper objectMapper;

    public IssueReportController(IssueReportCommandService issueReportCommandService, IssueReportQueryService issueReportQueryService,
                                 ImageStorageService imageStorageService, ObjectMapper objectMapper) {
        this.issueReportCommandService = issueReportCommandService;
        this.issueReportQueryService = issueReportQueryService;
        this.imageStorageService = imageStorageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/issue-report", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new Issue Report", description = "Multipart form: part 'issueReport' = JSON object (type or paste below). Part 'image' = image file only (optional).")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, encoding = @Encoding(name = "issueReport", contentType = APPLICATION_JSON_VALUE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Issue Report created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")})
    public ResponseEntity<IssueReportResource> createIssueReport(
            @RequestPart("issueReport") CreateIssueReportResource resource,
            @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        String imgUrl = null;
        if (image != null && !image.isEmpty()) imgUrl = imageStorageService.upload(image.getBytes(), "issue-reports");
        var createCommand = CreateIssueReportCommandFromResourceAssembler.toCommandFromResource(resource, imgUrl);
        var issueReportId = issueReportCommandService.handle(createCommand);
        if (issueReportId == null) return ResponseEntity.badRequest().build();
        var issueReport = issueReportQueryService.handle(new GetIssueReportByIdQuery(issueReportId));
        if (issueReport.isEmpty()) return ResponseEntity.notFound().build();
        return new ResponseEntity<>(IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReport.get()), HttpStatus.CREATED);
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

    @PatchMapping(value = "/{issueReportId}/status", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update issue report status", description = "Updates the status of a issue report. Body: { \"status\": \"OPEN\" | \"IN_PROGRESS\" | \"CLOSED\" }")
    @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateIssueReportStatusResource.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Report status updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")
    })
    public ResponseEntity<IssueReportResource> updateIssueReportStatus(@PathVariable UUID issueReportId, HttpServletRequest request) throws IOException {
        UpdateIssueReportStatusResource resource = objectMapper.readValue(
                StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8),
                UpdateIssueReportStatusResource.class);
        if (resource == null) return ResponseEntity.badRequest().build();
        var command = UpdateIssueReportStatusCommandFromResourceAssembler.toCommandFromResource(issueReportId, resource);
        var updateIssueReport = issueReportCommandService.handle(command);
        if (updateIssueReport.isEmpty()) return ResponseEntity.badRequest().build();
        var issueReportEntity = updateIssueReport.get();
        var issueReportResource = IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReportEntity);
        return ResponseEntity.ok(issueReportResource);
    }

    @PatchMapping(value = "/{issueReportId}/assignee", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update issue report assignee", description = "Updates the assignee of a issue report. Body: { \"assigneeId\": \"uuid\" }")
    @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = UpdateIssueReportAssigneeResource.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Report assignee updated"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Issue Report not found")
    })
    public ResponseEntity<IssueReportResource> updateIssueReportAssignee(@PathVariable UUID issueReportId, HttpServletRequest request) throws IOException {
        UpdateIssueReportAssigneeResource resource = objectMapper.readValue(
                StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8),
                UpdateIssueReportAssigneeResource.class);
        if (resource == null) return ResponseEntity.badRequest().build();
        var command = UpdateIssueReportAssigneeCommandFromResourceAssembler.toCommandFromResource(issueReportId, resource);
        var updateIssueReport = issueReportCommandService.handle(command);
        if (updateIssueReport.isEmpty()) return ResponseEntity.badRequest().build();
        var issueReportEntity = updateIssueReport.get();
        var issueReportResource = IssueReportResourceFromEntityAssembler.toResourceFromEntity(issueReportEntity);
        return ResponseEntity.ok(issueReportResource);
    }

    @GetMapping("/{companyId}/filter")
    @Operation(summary = "Get Issue Reports by filters", description = "Get available Issue Reports in the system by filters.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Issue Reports retrieved successfully.")})
    public ResponseEntity<List<IssueReportResource>> getIssueReportsByFilters(
            @PathVariable UUID companyId,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) UUID assigneeId,
            @RequestParam(required = false) UUID reporterId,
            @RequestParam(required = false) Severity severity,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) UUID screenLocationId
    ) {
        var getIssueReportsByFiltersQuery = new GetIssueReportsByFiltersQuery(
                companyId, title, assigneeId, reporterId,
                severity != null ? severity.name() : null,
                status != null ? status.name() : null,
                screenLocationId
        );
        var issueReports = issueReportQueryService.handle(getIssueReportsByFiltersQuery);
        var issueReportResources = issueReports.stream()
                .map(IssueReportResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(issueReportResources);
    }
}
