package com.acme.tickit.tickitbackend.iam.interfaces.acl;

import java.util.UUID;

public interface UserContextFacade {
    Boolean ExistsUserById(UUID userId);
}
