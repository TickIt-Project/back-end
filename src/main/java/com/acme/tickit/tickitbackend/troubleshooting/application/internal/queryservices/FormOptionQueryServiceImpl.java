package com.acme.tickit.tickitbackend.troubleshooting.application.internal.queryservices;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllFormOptionsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetFormOptionByIdQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FormOptionQueryService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FormOptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FormOptionQueryServiceImpl implements FormOptionQueryService {
    private final FormOptionRepository formOptionRepository;

    public FormOptionQueryServiceImpl(FormOptionRepository formOptionRepository) {
        this.formOptionRepository = formOptionRepository;
    }

    @Override
    public Optional<FormOption> handle(GetFormOptionByIdQuery query) {
        return this.formOptionRepository.findById(query.formOptionId());
    }

    @Override
    public List<FormOption> handle(GetAllFormOptionsQuery query) {
        return this.formOptionRepository.findByField_Id(query.fieldId());
    }
}
