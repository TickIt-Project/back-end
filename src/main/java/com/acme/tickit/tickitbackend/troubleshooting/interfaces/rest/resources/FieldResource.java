package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import java.util.List;
import java.util.UUID;

public record FieldResource(
        UUID id,
        String fieldName,
        String description,
        String fieldType,
        Boolean isMandatory,
        Boolean othersAvailable,
        Double infNumLimit,
        Double supNumLimit,
        Integer infWordsLimit,
        Integer supWordsLimit,
        Integer infCharactersLimit,
        Integer supCharactersLimit,
        List<FormOptionResource> formOptions
) {
}
