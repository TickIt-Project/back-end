package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.shared.domain.model.valueobjects.CompanyID;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllCategoriesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCategoryByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.CategoryQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryQueryServiceImpl implements CategoryQueryService {
    private final CategoryRepository categoryRepository;

    public CategoryQueryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Category> handle(GetCategoryByIdQuery query) {
        return this.categoryRepository.findById(query.categoryId());
    }

    @Override
    public List<Category> handle(GetAllCategoriesQuery query) {
        return this.categoryRepository.findAllByCompanyId(new CompanyID(query.companyId()));
    }
}
