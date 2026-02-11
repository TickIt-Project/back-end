package com.acme.tickit.tickitbackend.iam.domain.model.commands;

import java.util.UUID;

public record UploadUserProfileImageCommand(
        UUID userId,
        byte[] image
) {}
