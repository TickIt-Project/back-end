package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllCompanyRolesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCompanyRoleByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CompanyRoleCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CompanyRoleQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CompanyRoleResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateCompanyRoleResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CompanyRoleResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CreateCompanyRoleCommandFromResourceAssembler;
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
@RequestMapping(value = "/api/v1/company-roles", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Company Roles", description = "Available Company Roles Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CompanyRoleController {
    private final CompanyRoleCommandService companyRoleCommandService;
    private final CompanyRoleQueryService companyRoleQueryService;

    public CompanyRoleController(CompanyRoleCommandService companyRoleCommandService, CompanyRoleQueryService companyRoleQueryService) {
        this.companyRoleCommandService = companyRoleCommandService;
        this.companyRoleQueryService = companyRoleQueryService;
    }

    @PostMapping("/company-role")
    @Operation(summary = "Create a new Company Roles", description = "Create a new Company Role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company Role created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Company Role not found")})
    public ResponseEntity<CompanyRoleResource> createCompanyRole(@RequestBody CreateCompanyRoleResource resource) {
        var createCommand = CreateCompanyRoleCommandFromResourceAssembler.toCommandFromResource(resource);
        var companyRoleId = companyRoleCommandService.handle(createCommand);
        if (companyRoleId == null) return ResponseEntity.badRequest().build();
        var getCompanyRoleByIdQuery = new GetCompanyRoleByIdQuery(companyRoleId);
        var companyRoleItem = companyRoleQueryService.handle(getCompanyRoleByIdQuery);
        if (companyRoleItem.isEmpty()) return ResponseEntity.notFound().build();
        var companyRoleEntity = companyRoleItem.get();
        var companyRoleResource = CompanyRoleResourceFromEntityAssembler.toResourceFromEntity(companyRoleEntity);
        return new ResponseEntity<>(companyRoleResource, HttpStatus.CREATED);
    }

    @GetMapping("/{companyRoleId}")
    @Operation(summary = "Get Company Role by id", description = "Get Company Role by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company Role found"),
            @ApiResponse(responseCode = "404", description = "Company Role not found")})
    public ResponseEntity<CompanyRoleResource> getCompanyRoleById(@PathVariable UUID companyRoleId) {
        var getCompanyRoleByIdQuery = new GetCompanyRoleByIdQuery(companyRoleId);
        var companyRole = companyRoleQueryService.handle(getCompanyRoleByIdQuery);
        if (companyRole.isEmpty()) return ResponseEntity.notFound().build();
        var companyRoleEntity = companyRole.get();
        var companyRoleResource = CompanyRoleResourceFromEntityAssembler.toResourceFromEntity(companyRoleEntity);
        return ResponseEntity.ok(companyRoleResource);
    }

    @GetMapping("/{companyId}/company-roles")
    @Operation(summary = "Get all Company Roles", description = "Get all available Company Roles in the system by companyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company Roles retrieved successfully.")})
    public ResponseEntity<List<CompanyRoleResource>> getAllCompanyRoles(@PathVariable UUID companyId) {
        var getAllCompanyRoleQuery = new GetAllCompanyRolesQuery(companyId);
        var companyRole = companyRoleQueryService.handle(getAllCompanyRoleQuery);
        var companyRoleResources = companyRole.stream()
                .map(CompanyRoleResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(companyRoleResources);
    }
}
