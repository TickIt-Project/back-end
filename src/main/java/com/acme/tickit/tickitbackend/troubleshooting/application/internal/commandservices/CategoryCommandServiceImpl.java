package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.shared.domain.exceptions.CompanyIdNotFoundException;
import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.application.internal.outboundservices.acl.ExternalCompanyService;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryAlreadyExistsExceptions;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNotFoundException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FieldNotFoundForCategoryException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.UpdateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.CategoryField;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CategoryCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CategoryRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FieldRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FormOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;
    private final FieldRepository fieldRepository;
    private final FormOptionRepository formOptionRepository;
    private final ExternalCompanyService externalCompanyService;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository,
                                      FieldRepository fieldRepository,
                                      FormOptionRepository formOptionRepository,
                                      ExternalCompanyService externalCompanyService) {
        this.categoryRepository = categoryRepository;
        this.fieldRepository = fieldRepository;
        this.formOptionRepository = formOptionRepository;
        this.externalCompanyService = externalCompanyService;
    }

    @Override
    public UUID handle(CreateCategoryCommand command) {
        if (!externalCompanyService.ExistsCompanyById(command.companyId()))
            throw new CompanyIdNotFoundException(command.companyId().toString());
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
                        field.addFormOption(new FormOption(optionName.trim(), field));
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

    @Override
    @Transactional
    public UUID handle(UpdateCategoryCommand command) {
        Category category = categoryRepository.findById(command.categoryId())
                .orElseThrow(() -> new CategoryNotFoundException(command.categoryId()));

        CompanyID companyId = category.getCompanyId();
        if (Boolean.TRUE.equals(categoryRepository.existsByCompanyIdAndNameAndIdNot(
                companyId, command.name(), command.categoryId()))) {
            throw new CategoryAlreadyExistsExceptions(command.name());
        }

        category.setName(command.name());
        category.setDescription(command.description());

        List<CategoryField> defs = command.fields();
        Set<UUID> requestedFieldIds = defs.stream()
                .map(CategoryField::fieldId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        for (CategoryField def : defs) {
            if (def.fieldId() != null && findFieldInCategory(category, def.fieldId()) == null) {
                throw new FieldNotFoundForCategoryException(def.fieldId());
            }
        }

        List<Field> toRemove = category.getFields().stream()
                .filter(f -> !requestedFieldIds.contains(f.getId()))
                .toList();
        for (Field f : toRemove) {
            category.removeField(f);
        }

        for (CategoryField def : defs) {
            if (def.fieldId() != null) {
                Field field = findFieldInCategory(category, def.fieldId());
                field.applyCategoryField(def);
                syncFormOptions(field, def.formOptions());
                fieldRepository.save(field);
            }
        }

        for (CategoryField def : defs) {
            if (def.fieldId() == null) {
                Field field = new Field(def, category);
                category.addField(field);
                fieldRepository.save(field);
                syncFormOptions(field, def.formOptions());
                formOptionRepository.saveAll(field.getFormOptions());
            }
        }

        categoryRepository.save(category);
        return category.getId();
    }

    private static Field findFieldInCategory(Category category, UUID fieldId) {
        return category.getFields().stream()
                .filter(f -> f.getId().equals(fieldId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Syncs form options by option name (trimmed): keep existing names, add new ones, remove missing ones.
     */
    private static void syncFormOptions(Field field, List<String> formOptions) {
        LinkedHashSet<String> wanted = new LinkedHashSet<>();
        if (formOptions != null) {
            for (String s : formOptions) {
                if (s != null && !s.isBlank()) {
                    wanted.add(s.trim());
                }
            }
        }

        List<FormOption> snapshot = new ArrayList<>(field.getFormOptions());
        for (FormOption opt : snapshot) {
            String name = opt.getOptionName() == null ? "" : opt.getOptionName().trim();
            if (!wanted.contains(name)) {
                field.removeFormOption(opt);
            }
        }

        for (String name : wanted) {
            if (!hasOptionWithTrimmedName(field, name)) {
                field.addFormOption(new FormOption(name, field));
            }
        }
    }

    private static boolean hasOptionWithTrimmedName(Field field, String trimmedName) {
        return field.getFormOptions().stream().anyMatch(o -> {
            if (o.getOptionName() == null) {
                return trimmedName.isEmpty();
            }
            return o.getOptionName().trim().equals(trimmedName);
        });
    }
}
