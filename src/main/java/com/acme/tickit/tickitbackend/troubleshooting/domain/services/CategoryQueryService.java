package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.aggregates.Category;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllCategoriesQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetCategoryByIdQuery;

import java.util.List;
import java.util.Optional;

public interface CategoryQueryService {
    Optional<Category> handle(GetCategoryByIdQuery query);
    List<Category> handle(GetAllCategoriesQuery query);
}
