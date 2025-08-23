package com.acme.tickit.tickitbackend.iam.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNotFoundException;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetAllUsersQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetCompanyByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.SignInQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.*;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.*;
import com.acme.tickit.tickitbackend.shared.infrastructure.security.JwtService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Available User Endpoints")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final JwtService jwtService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService, JwtService jwtService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.jwtService = jwtService;
    }

    @PostMapping("/users/sign-up")
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

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Get all users for the current tenant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully.")})
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var getAllUsersQuery = new GetAllUsersQuery();
        var users = userQueryService.handle(getAllUsersQuery);
        var userResources = users.stream()
                .map(UserResourceFromEntityAssembler::toResourceFromEntity)
                .toList();
        return ResponseEntity.ok(userResources);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticatedUserResource> signIn(@RequestBody SignInResource body) {
        var query = new SignInQuery(body.username(), body.password());
        String token = userQueryService.handle(query);

        String userId    = jwtService.extractClaim(token, Claims::getSubject);
        String username  = jwtService.extractClaim(token, c -> c.get("username", String.class));
        String role      = jwtService.extractClaim(token, c -> c.get("role", String.class));
        String companyId = jwtService.extractClaim(token, c -> c.get("companyId", String.class));

        var resource = new AuthenticatedUserResource(
                token,
                java.util.UUID.fromString(userId),
                username,
                role,
                companyId
        );
        return ResponseEntity.ok(resource);
    }
}

/**
 * usar plan B WAAAAAAAAAA
 */