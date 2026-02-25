package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.CategoryResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FieldResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FormOptionResource;

public class CategoryResourceFromEntityAssembler {
    public static CategoryResource toResourceFromEntity(Category entity) {
        return new CategoryResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getFields()
                        .stream()
                        .map(field -> new FieldResource(
                                field.getId(),
                                field.getFieldName(),
                                field.getDescription(),
                                field.getFieldType().name(),
                                field.getIsMandatory(),
                                field.getOthersAvailable(),
                                field.getInfNumLimit(),
                                field.getSupNumLimit(),
                                field.getInfWordsLimit(),
                                field.getSupWordsLimit(),
                                field.getInfCharactersLimit(),
                                field.getSupCharactersLimit(),
                                field.getFormOptions()
                                        .stream()
                                        .map(opt -> new FormOptionResource(
                                                opt.getId(),
                                                opt.getOptionName()
                                        )).toList()
                        )).toList()
        );
    }
}
