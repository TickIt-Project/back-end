package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryAlreadyExistsExceptions;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.CategoryNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateCategoryCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CategoryCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryCommandServiceImpl implements CategoryCommandService {
    private final CategoryRepository categoryRepository;

    public CategoryCommandServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public UUID handle(CreateCategoryCommand command) {
        if (categoryRepository.existsByCompanyIdAndName(new CompanyID(command.companyId()), command.name()))
            throw new CategoryAlreadyExistsExceptions(command.name());
        var category = new Category(command);
        try {
            categoryRepository.save(category);
        } catch (Exception ex) {
            throw new CategoryNotCreatedException(ex.getMessage());
        }
        return category.getId();
    }
}
