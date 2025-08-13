package com.acme.tickit.tickitbackend.iam.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetCompanyByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.CompanyCommandService;
import com.acme.tickit.tickitbackend.iam.domain.services.CompanyQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CompanyResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CreateCompanyResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.CompanyResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.CreateCompanyCommandFromResourceAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/companies", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Company", description = "Available Company Endpoints")
public class CompanyController {
    private final CompanyCommandService companyCommandService;
    private final CompanyQueryService companyQueryService;

    public CompanyController(CompanyCommandService companyCommandService, CompanyQueryService companyQueryService) {
        this.companyCommandService = companyCommandService;
        this.companyQueryService = companyQueryService;
    }

    @PostMapping("/companies")
    @Operation(summary = "Create a new Company", description = "Create a new Company")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Company not found")})
    public ResponseEntity<CompanyResource> createCategory(@RequestBody CreateCompanyResource resource) {
        var createCommand = CreateCompanyCommandFromResourceAssembler.toCommandFromResource(resource);
        var companyId = companyCommandService.handle(createCommand);
        if (companyId == null) return ResponseEntity.badRequest().build();
        var getCompanyByIdQuery = new GetCompanyByIdQuery(companyId);
        var company = companyQueryService.handle(getCompanyByIdQuery);
        if (company.isEmpty()) return ResponseEntity.notFound().build();
        var companyEntity = company.get();
        var companyResource = CompanyResourceFromEntityAssembler.toResourceFromEntity(companyEntity);
        return new ResponseEntity<>(companyResource, HttpStatus.CREATED);
    }
}
