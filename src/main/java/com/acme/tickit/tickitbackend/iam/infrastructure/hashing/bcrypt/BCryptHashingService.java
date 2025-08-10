package com.acme.tickit.tickitbackend.iam.infrastructure.hashing.bcrypt;

import com.acme.tickit.tickitbackend.iam.application.internal.outboundservices.HashingService;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface BCryptHashingService extends HashingService, PasswordEncoder {
    String encode(CharSequence rawPassword);

    boolean matches(CharSequence rawPassword, String encodedPassword);
}
