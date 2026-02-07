package com.acme.tickit.tickitbackend.shared.infrastructure.cloudinary;

import com.acme.tickit.tickitbackend.shared.application.external.ImageStorageService;
import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CloudinaryService implements ImageStorageService {

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String upload(byte[] file, String folder) {
        try {
            Map<?, ?> result = cloudinary.uploader().upload(
                    file,
                    Map.of("folder", folder)
            );
            return result.get("secure_url").toString();
        } catch (Exception e) {
            throw new RuntimeException("Cloudinary upload failed", e);
        }
    }
}

