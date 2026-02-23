package com.acme.tickit.tickitbackend.troubleshooting.domain.services;

import com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities.Field;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetAllFieldsQuery;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.queries.GetFieldByIdQuery;

import java.util.List;
import java.util.Optional;

public interface FieldQueryService {
    Optional<Field> handle(GetFieldByIdQuery query);
    List<Field> handle(GetAllFieldsQuery query);
}
