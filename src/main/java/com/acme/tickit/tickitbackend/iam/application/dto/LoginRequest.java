package com.acme.tickit.tickitbackend.iam.application.dto;

public record LoginRequest(
        String username,
        String password
) {}
