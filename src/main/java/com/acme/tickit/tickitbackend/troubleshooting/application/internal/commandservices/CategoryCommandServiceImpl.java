package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryAlreadyExistsExceptions;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CategoryCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CategoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FieldRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FormOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;
    private final FieldRepository fieldRepository;
    private final FormOptionRepository formOptionRepository;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository,
                                      FieldRepository fieldRepository,
                                      FormOptionRepository formOptionRepository) {
        this.categoryRepository = categoryRepository;
        this.fieldRepository = fieldRepository;
        this.formOptionRepository = formOptionRepository;
    }

    @Override
    public UUID handle(CreateCategoryCommand command) {
        if (categoryRepository.existsByCompanyIdAndName(new CompanyID(command.companyId()), command.name()))
            throw new CategoryAlreadyExistsExceptions(command.name());
        var category = new Category(command);
        try {
            categoryRepository.save(category);
            List<CategoryField> fieldDefs = command.fields();
            for (CategoryField def : fieldDefs) {
                var field = new Field(def, category);
                category.addField(field);
                List<String> optionNames = def.formOptions() != null ? def.formOptions() : List.of();
                for (String optionName : optionNames) {
                    if (optionName != null && !optionName.isBlank()) {
                        field.addFormOption(new FormOption(optionName, field));
                    }
                }
                fieldRepository.save(field);
                formOptionRepository.saveAll(field.getFormOptions());
            }
            if (!fieldDefs.isEmpty()) {
                categoryRepository.save(category);
            }
        } catch (Exception ex) {
            throw new CategoryNotCreatedException(ex.getMessage());
        }
        return category.getId();
    }
}
