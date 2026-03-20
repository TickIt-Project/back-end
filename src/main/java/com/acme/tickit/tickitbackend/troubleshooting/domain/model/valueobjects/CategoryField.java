package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIsMandatoryNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldTypeNotAcceptedException;

import java.util.List;
import java.util.UUID;

/**
 * Field data for embedding in other commands (e.g. create category with fields).
 * Same shape as CreateFieldCommand but without categoryId.
 *
 * @param fieldId optional; {@code null} when creating a new field, non-null when updating an existing field.
 */
public record CategoryField(
        String fieldName,
        String description,
        String fieldType,
        Boolean isMandatory,
        Boolean othersAvailable,
        Double infNumLimit, Double supNumLimit,
        Integer infWordsLimit, Integer supWordsLimit,
        Integer infCharactersLimit, Integer supCharactersLimit,
        List<String> formOptions,
        UUID fieldId
) {
    public CategoryField {
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
