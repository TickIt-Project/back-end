package com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIsMandatoryNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldTypeNotAcceptedException;

import java.util.List;

/**
 * Field data for embedding in other commands (e.g. create category with fields).
 * Same shape as CreateFieldCommand but without categoryId.
 */
public record CategoryField(
        String fieldName,
        String fieldType,
        Boolean isMandatory,
        Boolean othersAvailable,
        Number infNumLimit, Number supNumLimit,
        Number infWordsLimit, Number supWordsLimit,
        Number infCharactersLimit, Number supCharactersLimit,
        List<String> formOptions
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
