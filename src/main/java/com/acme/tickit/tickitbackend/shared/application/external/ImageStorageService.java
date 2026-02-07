package com.acme.tickit.tickitbackend.shared.application.external;

/**
 * Port for storing images and returning their public URLs.
 * Implemented by infrastructure (e.g. Cloudinary).
 */
public interface ImageStorageService {

    String upload(byte[] file, String folder);
}
