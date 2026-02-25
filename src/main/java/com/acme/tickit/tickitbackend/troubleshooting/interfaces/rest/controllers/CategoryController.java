package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllCategoriesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCategoryByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CategoryCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CategoryQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CategorySelectResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateCategoryResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CategoryResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CategorySelectResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CreateCategoryCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform.CategoryResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/categories", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Categories", description = "Available Categories Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryCommandService categoryCommandService;
    private final CategoryQueryService categoryQueryService;

    public CategoryController(CategoryCommandService categoryCommandService, CategoryQueryService categoryQueryService) {
        this.categoryCommandService = categoryCommandService;
        this.categoryQueryService = categoryQueryService;
    }

    @PostMapping("/category")
    @Operation(summary = "Create a new Category", description = "Create a new Category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Category not found")})
    public ResponseEntity<CategoryResource> createCategory(@RequestBody CreateCategoryResource resource) {
        var createCommand = CreateCategoryCommandFromResourceAssembler.toCommandFromResource(resource);
        var categoryId = categoryCommandService.handle(createCommand);
        if (categoryId == null) return ResponseEntity.badRequest().build();
        var getCategoryByIdQuery = new GetCategoryByIdQuery(categoryId);
        var categoryItem = categoryQueryService.handle(getCategoryByIdQuery);
        if (categoryItem.isEmpty()) return ResponseEntity.notFound().build();
        var categoryEntity = categoryItem.get();
        var categoryResource = CategoryResourceFromEntityAssembler.toResourceFromEntity(categoryEntity);
        return new ResponseEntity<>(categoryResource, HttpStatus.CREATED);
    }

    @GetMapping("/{categoryId}")
    @Operation(summary = "Get Category by id", description = "Get Category by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Category found"),
            @ApiResponse(responseCode = "404", description = "Category not found")})
    public ResponseEntity<CategorySelectResource> getCategoryById(@PathVariable UUID categoryId) {
        var getCategoryByIdQuery = new GetCategoryByIdQuery(categoryId);
        var category = categoryQueryService.handle(getCategoryByIdQuery);
        if (category.isEmpty()) return ResponseEntity.notFound().build();
        var categoryEntity = category.get();
        var categoryResource = CategorySelectResourceFromEntityAssembler.toResourceFromEntity(categoryEntity);
        return ResponseEntity.ok(categoryResource);
    }

    @GetMapping("/{companyId}/categories")
    @Operation(summary = "Get all Categories", description = "Get all available Categories in the system by companyId.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully.")})
    public ResponseEntity<List<CategoryResource>> getAllCategories(@PathVariable UUID companyId) {
        var getAllCategoryQuery = new GetAllCategoriesQuery(companyId);
        var categories = categoryQueryService.handle(getAllCategoryQuery);
        var categoryResources = categories.stream()
                .map(CategoryResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(categoryResources);
    }
}
