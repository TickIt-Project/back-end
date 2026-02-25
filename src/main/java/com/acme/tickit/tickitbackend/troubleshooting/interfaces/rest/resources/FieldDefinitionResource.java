package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIsMandatoryNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldTypeNotAcceptedException;

import java.util.List;

/** Field data for embedding in create-category request (no categoryId). */
public record FieldDefinitionResource(
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
        List<String> formOptions
) {
    public FieldDefinitionResource {
        if (fieldName == null || fieldName.isEmpty()) {
            throw new FieldNameNotAcceptedException();
        }
        if (fieldType == null || fieldType.isEmpty()) {
            throw new FieldTypeNotAcceptedException();
        }
        if (isMandatory == null) {
            throw new FieldIsMandatoryNotAcceptedException();
        }
        if (othersAvailable == null) {
            othersAvailable = false;
        }
    }
}
