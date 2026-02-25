package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CreateCategoryResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FieldDefinitionResource;

import java.util.List;
import java.util.stream.Collectors;

public class CreateCategoryCommandFromResourceAssembler {
    public static CreateCategoryCommand toCommandFromResource(CreateCategoryResource resource) {
        List<CategoryField> fieldDefs = resource.fields() == null ? null : resource.fields().stream()
                .map(CreateCategoryCommandFromResourceAssembler::toFieldDefinition)
                .collect(Collectors.toList());
        return new CreateCategoryCommand(
                resource.companyId(),
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
                r.formOptions()
        );
    }
}
