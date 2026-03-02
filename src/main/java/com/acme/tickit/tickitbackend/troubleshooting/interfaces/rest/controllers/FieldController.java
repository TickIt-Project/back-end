package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllFieldsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetFieldByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FieldCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FieldQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateFieldResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FieldResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CreateFieldCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.FieldResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/fields", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Fields", description = "Available Fields Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class FieldController {
    private final FieldCommandService fieldCommandService;
    private final FieldQueryService fieldQueryService;

    public FieldController(FieldCommandService fieldCommandService, FieldQueryService fieldQueryService) {
        this.fieldCommandService = fieldCommandService;
        this.fieldQueryService = fieldQueryService;
    }

    @PostMapping("/field")
    @Operation(summary = "Create a new Field", description = "Create a new Field")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Field created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Field not found")})
    public ResponseEntity<FieldResource> createField(@RequestBody CreateFieldResource resource) {
        var createCommand = CreateFieldCommandFromResourceAssembler.toCommandFromResource(resource);
        var fieldId = fieldCommandService.handle(createCommand);
        if (fieldId == null) return ResponseEntity.badRequest().build();
        var getFieldByIdQuery = new GetFieldByIdQuery(fieldId);
        var fieldItem = fieldQueryService.handle(getFieldByIdQuery);
        if (fieldItem.isEmpty()) return ResponseEntity.notFound().build();
        var fieldEntity = fieldItem.get();
        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(fieldEntity);
        return new ResponseEntity<>(fieldResource, HttpStatus.CREATED);
    }

    @GetMapping("/{fieldId}")
    @Operation(summary = "Get Field by id", description = "Get Field by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Field found"),
            @ApiResponse(responseCode = "404", description = "Field not found")})
    public ResponseEntity<FieldResource> getFieldById(@PathVariable UUID fieldId) {
        var getFieldByIdQuery = new GetFieldByIdQuery(fieldId);
        var field = fieldQueryService.handle(getFieldByIdQuery);
        if (field.isEmpty()) return ResponseEntity.notFound().build();
        var fieldEntity = field.get();
        var fieldResource = FieldResourceFromEntityAssembler.toResourceFromEntity(fieldEntity);
        return ResponseEntity.ok(fieldResource);
    }

    @GetMapping("/{categoryId}/fields")
    @Operation(summary = "Get all Fields", description = "Get all available Fields in the system by categoryId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fields retrieved successfully.")})
    public ResponseEntity<List<FieldResource>> getAllFields(@PathVariable UUID categoryId) {
        var getAllFieldQuery = new GetAllFieldsQuery(categoryId);
        var fields = fieldQueryService.handle(getAllFieldQuery);
        var fieldResources = fields.stream()
                .map(FieldResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(fieldResources);
    }
}
