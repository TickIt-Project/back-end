package com.acme.tickit.tickitbackend.troubleshooting.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.ScreenLocation;
import com.acme.tickit.tickitbackend.troubleshooting.rest.resources.ScreenLocationResource;

public class ScreenLocationResourceFromEntityAssembler {
    public static ScreenLocationResource toResourceFromEntity(ScreenLocation entity) {
        return new ScreenLocationResource(
                entity.getId(),
                entity.getCompanyId().companyId(),
                entity.getName(),
                entity.getUrl()
        );
    }
}
