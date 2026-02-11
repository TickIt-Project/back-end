package com.acme.tickit.tickitbackend.iam.interfaces.rest.controllers;

import com.acme.tickit.tickitbackend.iam.domain.model.queries.GetUserByIdQuery;
import com.acme.tickit.tickitbackend.iam.domain.services.UserCommandService;
import com.acme.tickit.tickitbackend.iam.domain.services.UserQueryService;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.AuthenticatedUserResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.CreateUserResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.SignInResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.resources.UserResource;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.AuthenticatedUserResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.CreateUserCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.SignInCommandFromResourceAssembler;
import com.acme.tickit.tickitbackend.iam.interfaces.rest.transform.UserResourceFromEntityAssembler;
import com.acme.tickit.tickitbackend.shared.application.external.ImageStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/authentication", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Available Authentication Endpoints")
public class AuthenticationController {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;
    private final ImageStorageService imageStorageService;
    private final ObjectMapper objectMapper;

    public AuthenticationController(UserCommandService userCommandService, UserQueryService userQueryService,
                                    ImageStorageService imageStorageService, ObjectMapper objectMapper) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
        this.imageStorageService = imageStorageService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/sign-up", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new User", description = "Multipart form: part 'user' = JSON object (type or paste below). Part 'profileImage' = image file only (optional).")
    @RequestBody(content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE, encoding = @Encoding(name = "user", contentType = APPLICATION_JSON_VALUE)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<UserResource> createUser(
            @RequestPart("user") CreateUserResource resource,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) throws IOException {
        String profileImageUrl = null;
        if (profileImage != null && !profileImage.isEmpty()) profileImageUrl = imageStorageService.upload(profileImage.getBytes(), "users/profile");
        var createCommand = CreateUserCommandFromResourceAssembler.toCommandFromResource(resource, profileImageUrl);
        var userId = userCommandService.handle(createCommand);
        if (userId == null) return ResponseEntity.badRequest().build();
        var getUserByIdQuery = new GetUserByIdQuery(userId);
        var user = userQueryService.handle(getUserByIdQuery);
        if (user.isEmpty()) return ResponseEntity.notFound().build();
        var userEntity = user.get();
        var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(userEntity);
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @PostMapping(value = "/sign-in", consumes = APPLICATION_JSON_VALUE)
    @Operation(summary = "Sign in as a user", description = "Sign in with username and password.")
    @RequestBody(required = true, content = @Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = SignInResource.class)))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User signed in"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "User not found")})
    public ResponseEntity<AuthenticatedUserResource> signIn(HttpServletRequest request) throws IOException {
        String body = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);
        SignInResource resource = objectMapper.readValue(body, SignInResource.class);
        var signInCommand = SignInCommandFromResourceAssembler.toCommandFromResource(resource);
        var authentication = userCommandService.handle(signInCommand);
        if (authentication.isEmpty()) return ResponseEntity.badRequest().build();
        var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler.toResourceFromEntity(authentication.get().getLeft(), authentication.get().getRight());
        return ResponseEntity.ok(authenticatedUserResource);
    }
}
