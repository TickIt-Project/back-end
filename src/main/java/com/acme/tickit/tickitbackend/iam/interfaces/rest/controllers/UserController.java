package com.acme.tickit.tickitbackend.iam.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.SignInCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUsersByRoleQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.*;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.*;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Available User Endpoints")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping("/sign-up")
    @Operation(summary = "Create a new User", description = "Create a new User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<UserResource> createCategory(@RequestBody CreateUserResource resource) {
        var createCommand = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource);
        var userId = userCommandService.handle(createCommand);
        if (userId == null) return ResponseEntity.badRequest().build();
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        var userEntity = user.get();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @GetMapping("/{companyId}")
    @Operation(summary = "Get all users", description = "Get all users for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully.")})
    public ResponseEntity<List<UserResource>> getAllUsers(@PathVariable UUID companyId) {
        var getAllUsersQuery = new GetAllUsersQuery(companyId);
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }

    @PostMapping("/sign-in")
    @Operation(summary = "Sign in as a user", description = "Sign in as any type of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User signed in"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource resource) {
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var companyId = userCommandService.handle(signInCommand);
        if (companyId == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(new AuthenticatedUserResource(companyId));
    }

    @GetMapping("/{companyId}/roles")
    @Operation(summary = "Get users by role", description = "Get all users by tenant and role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully."),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters."),
            @ApiResponse(responseCode = "404", description = "Company or role not found."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    public ResponseEntity<List<UserResource>> getUsersByRole(@PathVariable UUID companyId, @RequestParam String role) {
        var getUsersByRoleQuery = new GetUsersByRoleQuery(companyId, role);
        var users = userQueryService.handle(getUsersByRoleQuery);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }
}
