package com.acme.tickit.tickitbackend.troubleshooting.domain.model.entities;

import com.acme.tickit.tickitbackend.shared.domain.model.entities.AuditableModel;
import com.acme.tickit.tickitbackend.troubleshooting.domain.model.valueobjects.FieldType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Field extends AuditableModel {

    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FieldType fieldType;

    private Boolean isMandatory;

    private Boolean othersAvailable;

    private Number infNumLimit;
    private Number supNumLimit;

    private Number infWordsLimit;
    private Number supWordsLimit;

    private Number infCharactersLimit;
    private Number supCharactersLimit;

    public Field() {}
}
