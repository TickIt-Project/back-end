package com.acme.tickit.tickitbackend.iam.infrastructure.hashing.bcrypt;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
