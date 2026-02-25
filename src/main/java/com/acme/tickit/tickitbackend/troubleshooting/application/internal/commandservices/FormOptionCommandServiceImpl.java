package com.acme.tickit.tickitbackend.troubleshooting.application.internal.commandservices;

import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FormOptionAlreadyExistsException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.exceptions.FormOptionNotCreatedException;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.commands.CreateFormOptionCommand;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.domain.services.FormOptionCommandService;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FieldRepository;
import com.acme.tickit.tickitbackend.troubleshooting.infrastructure.persistence.jpa.repositories.FormOptionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FormOptionCommandServiceImpl implements FormOptionCommandService {
    private final FormOptionRepository formOptionRepository;
    private final FieldRepository fieldRepository;

    public FormOptionCommandServiceImpl(FormOptionRepository formOptionRepository, FieldRepository fieldRepository) {
        this.formOptionRepository = formOptionRepository;
        this.fieldRepository = fieldRepository;
    }

    @Override
    public UUID handle(CreateFormOptionCommand command) {
        if (formOptionRepository.existsByField_IdAndOptionName(command.fieldId(), command.optionName()))
            throw new FormOptionAlreadyExistsException(command.optionName());
        var field = fieldRepository.findById(command.fieldId()).orElseThrow();
        var option = new FormOption(command.optionName(), field);
        field.addFormOption(option);
        try {
            formOptionRepository.save(option);
            fieldRepository.save(field);
        } catch (Exception ex) {
            throw new FormOptionNotCreatedException(ex.getMessage());
        }
        return option.getId();
    }
}
