package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIsMandatoryNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldTypeNotAcceptedException;

import java.util.UUID;

public record CreateFieldResource(
        UUID categoryId,
        String fieldName,
        String fieldType,
        Boolean isMandatory,
        Boolean othersAvailable,
        Number infNumLimit,
        Number supNumLimit,
        Number infWordsLimit,
        Number supWordsLimit,
        Number infCharactersLimit,
        Number supCharactersLimit
) {
    public CreateFieldResource {
        if (categoryId == null) {
            throw new CategoryIdNotAcceptedException();
        }
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
