package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllFormOptionsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetFormOptionByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FormOptionCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FormOptionQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateFormOptionResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FormOptionResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CreateFormOptionCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.FormOptionResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/form-options", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Form Options", description = "Available Form Options Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class FormOptionController {
    private final FormOptionCommandService formOptionCommandService;
    private final FormOptionQueryService formOptionQueryService;

    public FormOptionController(FormOptionCommandService formOptionCommandService, FormOptionQueryService formOptionQueryService) {
        this.formOptionCommandService = formOptionCommandService;
        this.formOptionQueryService = formOptionQueryService;
    }

    @PostMapping("/form-option")
    @Operation(summary = "Create a new Form Option", description = "Create a new Form Option")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Form Option created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Form Option not found")})
    public ResponseEntity<FormOptionResource> createFormOption(@RequestBody CreateFormOptionResource resource) {
        var createCommand = CreateFormOptionCommandFromResourceAssembler.toCommandFromResource(resource);
        var formOptionId = formOptionCommandService.handle(createCommand);
        if (formOptionId == null) return ResponseEntity.badRequest().build();
        var getFormOptionByIdQuery = new GetFormOptionByIdQuery(formOptionId);
        var formOptionItem = formOptionQueryService.handle(getFormOptionByIdQuery);
        if (formOptionItem.isEmpty()) return ResponseEntity.notFound().build();
        var formOptionEntity = formOptionItem.get();
        var formOptionResource = FormOptionResourceFromEntityAssembler.toResourceFromEntity(formOptionEntity);
        return new ResponseEntity<>(formOptionResource, HttpStatus.CREATED);
    }

    @GetMapping("/{formOptionId}")
    @Operation(summary = "Get Form Option by id", description = "Get Form Option by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Form Option found"),
            @ApiResponse(responseCode = "404", description = "Form Option not found")})
    public ResponseEntity<FormOptionResource> getFormOptionById(@PathVariable UUID formOptionId) {
        var getFormOptionByIdQuery = new GetFormOptionByIdQuery(formOptionId);
        var formOption = formOptionQueryService.handle(getFormOptionByIdQuery);
        if (formOption.isEmpty()) return ResponseEntity.notFound().build();
        var formOptionEntity = formOption.get();
        var formOptionResource = FormOptionResourceFromEntityAssembler.toResourceFromEntity(formOptionEntity);
        return ResponseEntity.ok(formOptionResource);
    }

    @GetMapping("/{fieldId}/form-options")
    @Operation(summary = "Get all Form Options", description = "Get all available Form Options in the system by fieldId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Form Options retrieved successfully.")})
    public ResponseEntity<List<FormOptionResource>> getAllFormOptions(@PathVariable UUID fieldId) {
        var getAllFormOptionQuery = new GetAllFormOptionsQuery(fieldId);
        var formOptions = formOptionQueryService.handle(getAllFormOptionQuery);
        var formOptionResources = formOptions.stream()
                .map(FormOptionResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(formOptionResources);
    }
}
