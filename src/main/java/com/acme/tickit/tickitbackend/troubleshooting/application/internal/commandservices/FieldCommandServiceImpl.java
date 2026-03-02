package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldAlreadyExistsException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFieldCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FieldCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CategoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FieldRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FormOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FieldCommandServiceImpl implements FieldCommandService {
    private final FieldRepository fieldRepository;
    private final CategoryRepository categoryRepository;
    private final FormOptionRepository formOptionRepository;

    public FieldCommandServiceImpl(FieldRepository fieldRepository, CategoryRepository categoryRepository,
                                  FormOptionRepository formOptionRepository) {
        this.fieldRepository = fieldRepository;
        this.categoryRepository = categoryRepository;
        this.formOptionRepository = formOptionRepository;
    }

    @Override
    public UUID handle(CreateFieldCommand command) {
        if (fieldRepository.existsByCategory_IdAndFieldName(command.categoryId(), command.fieldName()))
            throw new FieldAlreadyExistsException(command.fieldName());
        var category = categoryRepository.findById(command.categoryId()).orElseThrow();
        var field = new Field(command, category);
        category.addField(field);
        List<String> optionNames = command.formOptions() != null ? command.formOptions() : List.of();
        for (String optionName : optionNames) {
            if (optionName != null && !optionName.isBlank()) {
                field.addFormOption(new FormOption(optionName, field));
            }
        }
        try {
            fieldRepository.save(field);
            categoryRepository.save(category);
            formOptionRepository.saveAll(field.getFormOptions());
        } catch (Exception e) {
            throw new FieldNotCreatedException(e.getMessage());
        }
        return field.getId();
    }
}
