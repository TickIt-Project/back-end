package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.FormOption;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllFormOptionsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetFormOptionByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FormOptionQueryService {
    Optional<FormOption> handle(GetFormOptionByIdQuery query);
    List<FormOption> handle(GetAllFormOptionsQuery query);
}
