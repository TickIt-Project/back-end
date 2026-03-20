package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FieldDefinitionResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.UpdateCategoryResource;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UpdateCategoryCommandFromResourceAssembler {

    public static UpdateCategoryCommand toCommandFromResource(UUID categoryId, UpdateCategoryResource resource) {
        List<CategoryField> fieldDefs = resource.fields() == null ? null : resource.fields().stream()
                .map(UpdateCategoryCommandFromResourceAssembler::toFieldDefinition)
                .collect(Collectors.toList());

        return new UpdateCategoryCommand(
                categoryId,
                resource.name(),
                resource.description(),
                fieldDefs
        );
    }

    private static CategoryField toFieldDefinition(FieldDefinitionResource r) {
        return new CategoryField(
                r.fieldName(),
                r.description(),
                r.fieldType(),
                r.isMandatory(),
                r.othersAvailable(),
                r.infNumLimit(),
                r.supNumLimit(),
                r.infWordsLimit(),
                r.supWordsLimit(),
                r.infCharactersLimit(),
                r.supCharactersLimit(),
                r.formOptions(),
                r.fieldId().orElse(null)
        );
    }
}

