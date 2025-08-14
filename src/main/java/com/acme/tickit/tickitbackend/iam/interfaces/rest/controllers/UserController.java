package com.acme.tickit.tickitbackend.iam.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetCompanyByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CompanyResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CreateCompanyResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CreateUserResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.UserResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.CompanyResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.CreateCompanyCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
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
@RequestMapping(value = "/api/v1/users", produces = APPLICATION_JSON_VALUE)
@Tag(name = "User", description = "Available User Endpoints")
public class UserController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping("/users")
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
}
