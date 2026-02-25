package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFieldCommand;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateFieldResource;

public class CreateFieldCommandFromResourceAssembler {
    public static CreateFieldCommand toCommandFromResource(CreateFieldResource resource) {
        return new CreateFieldCommand(
                resource.categoryId(),
                resource.fieldName(),
                resource.fieldType(),
                resource.isMandatory(),
                resource.othersAvailable(),
                resource.infNumLimit(),
                resource.supNumLimit(),
                resource.infWordsLimit(),
                resource.supWordsLimit(),
                resource.infCharactersLimit(),
                resource.supCharactersLimit()
        );
    }
}
