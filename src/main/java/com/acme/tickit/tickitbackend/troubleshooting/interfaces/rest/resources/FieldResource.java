package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

public record FieldResource(
        UUID id,
        String fieldName,
        String fieldType,
        Boolean isMandatory,
        Boolean othersAvailable,
        Number infNumLimit,
        Number supNumLimit,
        Number infWordsLimit,
        Number supWordsLimit,
        Number infCharactersLimit,
        Number supCharactersLimit,
        List<FormOptionResource> formOptions
) {
}
