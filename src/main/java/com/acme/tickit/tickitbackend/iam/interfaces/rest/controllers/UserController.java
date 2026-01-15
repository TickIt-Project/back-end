package com.acme.tickit.tickitbackend.iam.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.iam.domain.model.commands.DeleteUserByIdCommand;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUsersByRoleQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.*;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Available User Endpoints")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
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

    @PatchMapping("/{companyId}/status")
    @Operation(summary = "Update user password", description = "Updates the password of a user based on its username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User password updated"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResource> updateUserPassword(@PathVariable UUID companyId, @RequestBody UpdateUserPasswordResource resource) {
        var command = UpdateUserPasswordCommandFromResourceAssembler.toCommandFromResource(companyId, resource);
        var updateUser = userCommandService.handle(command);
        if (updateUser.isEmpty()) return ResponseEntity.badRequest().build();
        var userEntity = updateUser.get();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return ResponseEntity.ok(userResource);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete User", description = "Delete User")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User deleted successfully."),
            @ApiResponse(responseCode = "404", description = "User not found.")})
    public ResponseEntity<UserResource> deleteUser(@PathVariable UUID userId) {
        DeleteUserByIdCommand command = new DeleteUserByIdCommand(userId);
        var user = userCommandService.handle(command);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        var userEntity = user.get();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return ResponseEntity.ok(userResource);
    }
}
