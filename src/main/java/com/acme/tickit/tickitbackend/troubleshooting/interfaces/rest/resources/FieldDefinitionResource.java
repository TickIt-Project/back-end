package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIsMandatoryNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldTypeNotAcceptedException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Field data for embedding in create-category request (no categoryId). */
public record FieldDefinitionResource(
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
        List<String> formOptions,
        Optional<UUID> fieldId
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
        if (fieldId == null) {
            fieldId = Optional.empty();
        }
    }
}
