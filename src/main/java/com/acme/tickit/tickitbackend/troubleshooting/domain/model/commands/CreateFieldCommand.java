package com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryIdNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldIsMandatoryNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNameNotAcceptedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldTypeNotAcceptedException;

import java.util.List;
import java.util.UUID;

public record CreateFieldCommand(
        UUID categoryId,
        String fieldName,
        String fieldType,
        Boolean isMandatory,
        Boolean othersAvailable,
        Number infNumLimit, Number supNumLimit,
        Number infWordsLimit, Number supWordsLimit,
        Number infCharactersLimit, Number supCharactersLimit,
        List<String> formOptions
) {
    public CreateFieldCommand {
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
