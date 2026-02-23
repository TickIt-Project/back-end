package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldAlreadyExistsException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFieldCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FieldCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CategoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FieldCommandServiceImpl implements FieldCommandService {
    private final FieldRepository fieldRepository;
    private final CategoryRepository categoryRepository;

    public FieldCommandServiceImpl(FieldRepository fieldRepository, CategoryRepository categoryRepository) {
        this.fieldRepository = fieldRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public UUID handle(CreateFieldCommand command) {
        if (fieldRepository.existsByCategory_IdAndFieldName(command.categoryId(), command.fieldName()))
            throw new FieldAlreadyExistsException(command.fieldName());
        var category = categoryRepository.findById(command.categoryId()).orElseThrow();
        var field = new Field(command, category);
        category.addField(field);
        try {
            fieldRepository.save(field);
            categoryRepository.save(category);
        } catch (Exception e) {
            throw new FieldNotCreatedException(e.getMessage());
        }
        return field.getId();
    }
}
