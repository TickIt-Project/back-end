package com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.transform;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FieldResource;
import com.acme.tickit.tickitbackend.troubleshooting.interfaces.rest.resources.FormOptionResource;

public class FieldResourceFromEntityAssembler {
    public static FieldResource toResourceFromEntity(Field entity) {
        return new FieldResource(
                entity.getId(),
                entity.getFieldName(),
                entity.getFieldType().name(),
                entity.getIsMandatory(),
                entity.getOthersAvailable(),
                entity.getInfNumLimit(),
                entity.getSupNumLimit(),
                entity.getInfWordsLimit(),
                entity.getSupWordsLimit(),
                entity.getInfCharactersLimit(),
                entity.getSupCharactersLimit(),
                entity.getFormOptions()
                        .stream()
                        .map(opt -> new FormOptionResource(
                                opt.getId(),
                                opt.getOptionName()
                        )).toList()
        );
    }
}
