package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FormOptionResource;

public class FormOptionResourceFromEntityAssembler {
    public static FormOptionResource toResourceFromEntity(FormOption entity) {
        return new FormOptionResource(
                entity.getId(),
                entity.getOptionName()
        );
    }
}
