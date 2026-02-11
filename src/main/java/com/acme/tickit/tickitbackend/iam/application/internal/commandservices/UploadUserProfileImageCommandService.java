package com.acme.tickit.tickitbackend.iam.application.internal.commandservices;

import com.acme.tickit.tickitbackend.shared.application.external.ImageStorageService;
import com.acme.tickit.tickitbackend.iam.domain.exceptions.UserNotFoundException;
import com.acme.tickit.tickitbackend.iam.domain.model.aggregates.User;
import com.acme.tickit.tickitbackend.iam.domain.model.commands.UploadUserProfileImageCommand;
import com.acme.tickit.tickitbackend.iam.infrastructure.persistence.jpa.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UploadUserProfileImageCommandService {

    private final UserRepository userRepository;
    private final ImageStorageService imageStorageService;

    public UploadUserProfileImageCommandService(
            UserRepository userRepository,
            ImageStorageService imageStorageService
    ) {
        this.userRepository = userRepository;
        this.imageStorageService = imageStorageService;
    }

    @Transactional
    public void handle(UploadUserProfileImageCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new UserNotFoundException(command.userId().toString()));

        String imageUrl = imageStorageService.upload(
                command.image(),
                "users/profile"
        );

        user.updateProfileImage(imageUrl);
        userRepository.save(user);
    }
}
