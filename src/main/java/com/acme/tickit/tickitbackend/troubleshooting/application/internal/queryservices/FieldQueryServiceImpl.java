package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllFieldsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetFieldByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FieldQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FieldRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FieldQueryServiceImpl implements FieldQueryService {
    private final FieldRepository fieldRepository;

    public FieldQueryServiceImpl(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public Optional<Field> handle(GetFieldByIdQuery query) {
        return this.fieldRepository.findById(query.fieldId());
    }

    @Override
    public List<Field> handle(GetAllFieldsQuery query) {
        return this.fieldRepository.findByCategory_Id(query.categoryId());
    }
}
